package com.booksms.authentication.application.usecase;

import com.booksms.authentication.application.BaseUsecase;
import com.booksms.authentication.application.model.SearchUserCriteria;
import com.booksms.authentication.application.model.UserModel;
import com.booksms.authentication.core.entity.Role;
import com.booksms.authentication.core.entity.UserCredential;
import com.booksms.authentication.core.exception.UpdateFailureException;
import com.booksms.authentication.core.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase implements BaseUsecase<UserModel, UserModel> {
    private final IUserRepository repository;
    private final FindRoleUseCase findRoleUseCase;

    @Override
    public UserModel execute(UserModel model) {
        UserCredential userCredential = null;
        if(model.getId() != null){
             userCredential = repository.findById(model.getId());
        }
        if(userCredential == null){
            userCredential = repository.findByEmail(model.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
        }
        userCredential = merge(userCredential, model);
        repository.save(userCredential).orElseThrow(
                 ()  -> new UpdateFailureException("update user failed,please try again")
         );

         return model;
    }

    public UserCredential merge(UserCredential credential,UserModel userModel) {
        if(userModel == null) {
            return credential;
        }
        if(userModel.getPhone() != null){
            credential.setPhone(userModel.getPhone());
        }
        if(userModel.getAddress() != null){
            credential.setAddress(userModel.getAddress());
        }
        if(userModel.getIsVerified() != null){
            credential.setIsVerified(userModel.getIsVerified());
        }
        if(userModel.getPassword() != null){
            credential.setPassword(userModel.getPassword());
        }
        if(userModel.getIsFirstVisit() != null){
            credential.setFirstVisit(userModel.getIsFirstVisit());
        }
        if(userModel.getIsBlocked() != null){
            credential.setIsBlocked(userModel.getIsBlocked());
        }
        if(userModel.getRoleName() != null){
            SearchUserCriteria searchUserCriteria = SearchUserCriteria.builder()
                    .key("name")
                    .operation(":")
                    .value(userModel.getRoleName())
                    .build();
           Role role = findRoleUseCase.execute(List.of(searchUserCriteria)).get(0);
            credential.getRoles().add(role);
        }
        return credential;
    }
}
