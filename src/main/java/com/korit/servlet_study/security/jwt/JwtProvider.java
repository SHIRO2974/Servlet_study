package com.korit.servlet_study.security.jwt;

import com.korit.servlet_study.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtProvider {

    private Key key;

    private static JwtProvider instance;

    private JwtProvider() {

        final String SECRET = "e9804065161f0101aaa8dc0dfb28757fcce6138a6bdbf857995a339d5db6d199b300ad9d38863ffbc7a5106752bfca65c2aa83a11b181ca1c1693fb5d84dc788f24798914a2d4cfaf377ea8ddc01267f6688f26ac499d8ee5fccc6d2312a5685507eb84b1146fdf337ae2ec49af012af50c23240dffaba08880aca09d58744248fa7494b413029f11144b3d30f386efaafa721c5db2c74b2dc1c18cf70eda97952f6699f423f6ae96a5755145675c15b471b1fb22ee720c33de9598b5ac8643b59d13c27f75da1b835d9f6ff29b7f0dcd68f8de6b19fdf8ad4c213945d4cd8c4f750213701fce6ef6f6d42174e3449ae41df2bafa4ce882f81407788bdeb7c51";
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public static JwtProvider getInstance() {

        if (instance == null) {
            instance = new JwtProvider();
        }
        return instance;
    }

    private Date getExpireDate() {

        return new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 365));   // 1년 뒤 만료
    }

    public String generateToken(User user) {    // 로그인이 되어진 User 를 전달

        return Jwts.builder()
                .claim("userId", user.getUserId())
                .setExpiration(getExpireDate()) // 토큰 만료시간
                .signWith(key, SignatureAlgorithm.HS256)    // 암호화 진행
                .compact();
    }

    // 토큰 인증 함수
    public Claims parseToken(String token) {

        Claims claims = null;
        try {

            claims = Jwts.parserBuilder()   // Jwts 로 만들어주는 빌더
                    .setSigningKey(key)     // 클라이언트에서 받은 키 값을 확인한다
                    .build()
                    .parseClaimsJws(removeBearer(token))  // remove 를 한 token 에 들어있는 Claims 을 가지고 온다
                    .getBody(); // 리턴값이 Claims 로 반환

        } catch (Exception e) {

            e.printStackTrace();
        }
        return claims;
    }

    private String removeBearer(String bearerToken) {

        String accessToken = null;
        final String BEARER_KEYWORD = "Bearer ";

        // BEARER_KEYWORD 으로 문자열로 시작했는지 확인
        if (bearerToken.startsWith(BEARER_KEYWORD)) {

            accessToken = bearerToken.substring(BEARER_KEYWORD.length());
        }
        //  토큰 인덱스부터 인덱스를 잘라서 accessToken 으로 대입
        return accessToken;
    }
}
