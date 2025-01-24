package com.korit.servlet_study.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dao.UserDao;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.entity.User;
import com.korit.servlet_study.security.annotation.JwtValid;
import com.korit.servlet_study.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class AuthenticationFilter implements Filter {

    private JwtProvider jwtProvider;
    private UserDao userDao;

    public AuthenticationFilter() {
        jwtProvider = JwtProvider.getInstance();
        userDao = UserDao.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 다운캐스팅을 하여 내부 메소드 사용
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {

            if(isJwtTokenValid(request)) {  // 인증이 필요한 요청인지 확인

                String bearerToken = request.getHeader("Authorization");

                if(bearerToken == null) {   // 토큰이 있는지 확인

                    setUnAuthenticatedResponse(response);
                    return; // 인증이 필요없는 요청이라면 함수를 벗어난다
                }

                Claims claims = jwtProvider.parseToken(bearerToken);

                if(claims == null) {
                    setUnAuthenticatedResponse(response);
                    return;
                }

                int userId = Integer.parseInt(claims.get("userId").toString());
                User foundUser = userDao.findById(userId);

                if(foundUser == null) { // userId 가 없다면
                    setUnAuthenticatedResponse(response);
                    return;
                }
            };

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isJwtTokenValid(HttpServletRequest request) throws ClassNotFoundException {

        String method = request.getMethod();    // 요청 메소드를 찾는다
        String servletPath = request.getHttpServletMapping().getServletName();  // 어떤 Servlet 에서 요청을 보냈는가 확인

        Class<?> servletClass = Class.forName(servletPath); //
        Method foundMethod = getMappedMethod(servletClass, method);
        return foundMethod != null; // 검사해야할 토큰이 있다
    }


    private Method getMappedMethod(Class<?> clazz, String methodName) {

        for (Method method : clazz.getDeclaredMethods()) {

            // 현재 메서드의 이름이 methodName 으로 끝나는지 확인하고, class 안에 JwtValid 어노테이션이 달려있는지 확인
            if(method.getName().toLowerCase().endsWith(methodName.toLowerCase()) && method.isAnnotationPresent(JwtValid.class)) {

                return method;

            }
    }
        return null;
    }

    private void setUnAuthenticatedResponse(HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDto<String> responseDto = ResponseDto.forbidden("검증 할 수 없는 Access Token 입니다.");
        response.setStatus(responseDto.getStatus());
        response.setContentType("application/json");
        response.getWriter().println(objectMapper.writeValueAsString(responseDto));
    }

}