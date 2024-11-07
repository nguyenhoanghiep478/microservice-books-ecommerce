package com.booksms.authentication.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer address;
    private String password;
    private String image;
    private Boolean isVerified;
    private Boolean isFirstVisit;
    private Boolean isBlocked;
    private Integer failAttempt;
    private LocalDateTime lockTime;
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name= "role_id")
    )
    private Set<Role> roles;

    public Set<Role> getRoles() {
        if(roles == null){
            roles = new HashSet<>();
        }
        return roles;
    }

    @PrePersist()
    public void prePersist(){
        if(failAttempt == null){
            failAttempt = 0;
        }
    }
}
