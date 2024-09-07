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
import com.booksms.authentication.core.exception.UserNotFoundException;
import com.booksms.authentication.interfaceLayer.DTO.Request.*;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import com.booksms.authentication.interfaceLayer.service.IJwtService;
import com.booksms.authentication.interfaceLayer.service.RedisService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseCookie;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
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
    private final KafkaTemplate<String, UserDTO> kafkaResetPasswordTemplate;
    private final UpdateUserUseCase updateUserUseCase;
    private final RedisService redisService;

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
                  .refreshToken(ResponseCookie.from("refresh-token",generateRefreshToken(request.getEmail()))
                          .httpOnly(true)
                          .secure(true)
                          .path("/")
                          .build())
                  .build();
       }else{
           throw new RuntimeException("Authentication Failed");
       }

    }

    private String generateRefreshToken(String email) {
        UserCredential userCredential = findUserUseCase.execute(List.of(SearchUserCriteria.builder()
                .key("email")
                .operation(":")
                .value(email)
                .build())
        ).get(0);
        return jwtService.generateRefreshToken(userCredential,getPermissionsByUserCredential(userCredential));
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

    @Override
    public AuthResponse refershToken(String jwt, String refreshToken) throws AuthenticationException {
        String token = jwt.substring(7);
        log.info(token);
        String id = jwtService.isValidToken(token);
        if(id == null){
           throw new AuthenticationException("invalid access token");
        }

        if(jwtService.isExpiredToken(refreshToken)){
            throw new AuthenticationException("invalid refresh token");
        }

        String email = jwtService.extractUsername(refreshToken);
        return AuthResponse.builder()
                .accessToken(generateToken(email))
                .refreshToken(ResponseCookie.from("refresh-token",generateRefreshToken(email))
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .build())
                .build();
    }

    @Override
    public void createResetPasswordRequest(CreateResetPasswordRequest request) {
        UserDTO userDTO = findById(request.getId());
        if(userDTO == null){
            throw new UserNotFoundException(String.format("User with id %s not found", request.getId()));
        }
        if(!userDTO.getEmail().equals(request.getEmail())){
            throw new BadRequestException("email address does not match");
        }
        redisService.setValue(userDTO.getId(),userDTO);
        kafkaResetPasswordTemplate.send("ResetPassword",userDTO);

    }

    @Override
    public void updatePassword(ResetPasswordRequest request) {
        UserDTO userDTO = redisService.getValue(request.getId());
        UserModel userModel = modelMapper.map(userDTO, UserModel.class);
        userModel.setPassword(passwordEncoder.encode(request.getPassword()));
        updateUserUseCase.execute(userModel);
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
