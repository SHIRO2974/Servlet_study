package com.korit.servlet_study.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet ("/api/authenticated")
public class AuthenticatedServlet  extends HttpServlet {

    private JwtProvider jwtProvider;

    public AuthenticatedServlet() {

        jwtProvider = JwtProvider.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bearerToken = req.getHeader("Authorization");    // bearerToken: JWT 토큰 명시
        // 클라이언트에서 받은 토큰: Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJleHAiOjE3NjkxMjgxNjV9.AVe_M9rUrRYEc-zVB7NyHacMNMliUQIqw2XFw9ibec0
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDto<?> responseDto = null;


        System.out.println(bearerToken);

        if (bearerToken == null) {

            responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
            resp.setStatus(responseDto.getStatus());
            resp.setContentType("application/json");
            resp.getWriter().println(objectMapper.writeValueAsString(responseDto));
            return;
        }

        Claims claims = jwtProvider.parseToken(bearerToken);

        if (claims == null) {

            responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
            resp.setStatus(responseDto.getStatus());
            resp.setContentType("application/json");
            resp.getWriter().println(responseDto);
            resp.getWriter().println(objectMapper.writeValueAsString(responseDto));
            return;
        }

        // claims 안에 있는 로그인한 계정의 ID를 응답
        responseDto = ResponseDto.success(claims.get("userId"));    // ResponseDto(status=200, message=success, body=17)

        resp.setStatus(responseDto.getStatus());
        resp.setContentType("application/json");
        resp.getWriter().println(responseDto);
        resp.getWriter().println(objectMapper.writeValueAsString(responseDto));
    }
}
