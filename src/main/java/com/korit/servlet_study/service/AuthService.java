package com.korit.servlet_study.service;

import com.korit.servlet_study.dao.AuthDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.dto.SigninDto;
import com.korit.servlet_study.dto.SignupDto;
import com.korit.servlet_study.entity.User;
import com.korit.servlet_study.security.jwt.JwtProvider;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private AuthDao authDao;
    private JwtProvider jwtProvider;

    private static AuthService instance;

    private AuthService() {

        authDao = AuthDao.getInstance();
        jwtProvider = JwtProvider.getInstance();
    }

    public static AuthService getInstance() {

        if (instance == null) {

            instance = new AuthService();
        }
        return instance;
    }

    public ResponseDto<?> signup(SignupDto signupDto) {

        User insertedUser = authDao.signup(signupDto.toUser());

        if(insertedUser == null) {

            return ResponseDto.fail("사용자를 추가하지 못하였습니다.");
        }
        return ResponseDto.success(insertedUser);
    }

    public ResponseDto<?> signin(SigninDto signinDto) {

        User foundUser = authDao.findUserByUsername(signinDto.getUsername());

        if(foundUser == null) { // username 정보가 있는지 확인

            return ResponseDto.fail("사용자 정보를 다시 확인하세요.");
        }
        // username 정보가 있다면 password 정보를 확인
        if(!BCrypt.checkpw(signinDto.getPassword(), foundUser.getPassword())) {

            return ResponseDto.fail("사용자 정보를 다시 확인하세요.");
        }
        // username 과 password 정보가 맞다면 foundUser 를 generateToken 으로 받아 응답
        return ResponseDto.success(jwtProvider.generateToken(foundUser));
    }
}