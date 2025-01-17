package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/user")
public class UserRestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = User.builder()
                .username("test")
                .password("1234")
                .name("테스트")
                .email("text@gmail.com")
                .build();

        String jsonUser = objectMapper.writeValueAsString(user);
        System.out.println(jsonUser);

        resp.setHeader("Access-Control-Allow-Origin", "*"); // 모든 서버에서 호출을 허용
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");   // 메서드의 POST, GET, OPTIONS 요청만 허용
        resp.setHeader("Access-Control-Allow-Headers", "Content-type"); //
        resp.setHeader("Access-Control-Allow-Credentials", "true"); // 브라우저에서 저장되어지는 쿠키를 허용

        resp.setContentType("application/json");
        resp.getWriter().println(jsonUser);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
