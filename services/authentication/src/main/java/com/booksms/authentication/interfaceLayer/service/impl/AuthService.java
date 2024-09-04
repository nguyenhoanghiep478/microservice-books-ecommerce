package com.booksms.authentication.interfaceLayer.service.impl;

import com.booksms.authentication.application.model.PermissionModel;
import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.application.usecase.FindUserUseCase;
import com.booksms.authentication.application.usecase.GetPermissionUseCase;
import com.booksms.authentication.application.usecase.RegisterUseCase;
import com.booksms.authentication.application.usecase.UpdateUserUseCase;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.EmailExistedException;
import com.booksms.authentication.core.exception.RegisterFailException;
import com.booksms.authentication.interfaceLayer.DTO.Request.AuthRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.NewUserRegister;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService  implements IAuthService {
    private final RegisterUseCase registerUseCase;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final GetPermissionUseCase getPermissionUseCase;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FindUserUseCase findUserUseCase;
    private final KafkaTemplate<String, NewUserRegister> kafkaTemplate;
    private final UpdateUserUseCase updateUserUseCase;

    @Override
    public UserDTO register(UserDTO credential) {
        try{
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));
           var user = registerUseCase.execute(modelMapper.map(credential, UserModel.class));
           kafkaTemplate.send("UserRegister", NewUserRegister.builder()
                           .firstName(user.getFirstName())
                           .lastName(user.getLastName())
                           .isVerified(false)
                           .isFirstVisit(true)
                           .recipient(user.getEmail())
                   .build());
           credential.setId(user.getId());
           return modelMapper.map(user, UserDTO.class);
        }catch (EmailExistedException e){
            log.error(e.getMessage());
            throw new EmailExistedException(e.getMessage());
        }catch (RegisterFailException e){
            throw new RegisterFailException(e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new InternalServerErrorException("please contact with administrator");
        }
    }

    public String generateToken(String username) {
        UserCredential userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                        .key("email")
                        .operation(":")
                        .value(username)
                .build())
        ).get(0);
        return jwtService.generateToken(userCredential,getPermissionsByUserCredential(userCredential));
    }

    @Override
    public Boolean validateToken(String jwt) {

        String token = jwt.substring(7);
        log.info(token);
        String id = jwtService.isValidToken(token);
        if(id == null){
            return false;
        }
        List<UserCredential> userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                .key("id")
                .operation(":")
                .value(id)
                .build())
        );
        return !userCredential.isEmpty();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

       if(authentication.isAuthenticated()){
          return AuthResponse.builder()
                  .accessToken(generateToken(request.getEmail()))
                  .build();
       }else{
           throw new RuntimeException("Authentication Failed");
       }

    }

    @Override
    public Integer getTotalUser() {
        return findUserUseCase.execute(null).size();
    }

    @Override
    public UserDTO findById(Integer id) {
        SearchUserCriteria fieldId=  SearchUserCriteria.builder()
                .key("id")
                .operation(":")
                .value(String.valueOf(id))
                .build();

        return modelMapper.map(findUserUseCase.execute(List.of(fieldId)).get(0), UserDTO.class);
    }


    private String[] getPermissionsByUserCredential(UserCredential userCredential) {
        Set<PermissionModel> permissionModels = getPermissionUseCase.execute(modelMapper.map(userCredential, UserModel.class));
        return permissionModels.stream().map(PermissionModel::getGroupCode).toArray(String[]::new);
    }

    @KafkaListener(id="consumer-user-register-response",topics = "UserRegisterResponse")
    private void verifyUserCredential(NewUserRegister userRegister) {
        UserModel user = modelMapper.map(userRegister, UserModel.class);
        user.setEmail(userRegister.getRecipient());
        updateUserUseCase.execute(user);
    }
}
