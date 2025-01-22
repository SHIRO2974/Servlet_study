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

    // SignupDto 객체를 받아 User 객체로 변환 후 DB에 저장, 응답 생성
    public ResponseDto<?> signup(SignupDto signupDto) {

        // DBMS 에 넣을 user 객체를 insertedUser 로 받는다
        User insertedUser = authDao.signup(signupDto.toUser());

        if(insertedUser == null) {  // DBMS 에 사용자가 추가되지 않았다면

            return ResponseDto.fail("사용자를 추가하지 못하였습니다.");
        }
        return ResponseDto.success(insertedUser);   // 추가된 사용자를 응답으로 반환
    }

    // SigninDto 객체를 받아 사용자 인증 후 JWT 를 생성하여 응답
    public ResponseDto<?> signin(SigninDto signinDto) {

        User foundUser = authDao.findUserByUsername(signinDto.getUsername());

        if(foundUser == null) { // DBMS 에 username 정보가 있는지 확인

            return ResponseDto.fail("사용자 정보를 다시 확인하세요.");
        }
        // username 정보가 있다면 Bcrypt 를 사용해 Password 를 검증
        if(!BCrypt.checkpw(signinDto.getPassword(), foundUser.getPassword())) {

            return ResponseDto.fail("사용자 정보를 다시 확인하세요.");
        }
        // username 과 password 정보가 맞다면 foundUser 를 JWTToken 으로 받아 응답
        return ResponseDto.success(jwtProvider.generateToken(foundUser));
    }
}