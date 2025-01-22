package com.korit.servlet_study.dto;

import com.korit.servlet_study.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    private String username;
    private String password;
    private String name;
    private String email;

    public User toUser() {
        return User.builder()
                .username(username)
                // BCrypt.gensalt 가 클수록 높은 암호화
                .password(BCrypt.hashpw(password, BCrypt.gensalt(10)))
                .name(name)
                .email(email)
                .build();
    }
}