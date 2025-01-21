package com.korit.servlet_study.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*")
public class corsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // ServletResponse 를 HttpServletResponse 으로 다운캐스팅해야 setHeader 메서드를 사용 가능
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Access-Control-Allow-Origin", "*"); // 모든 서버에서 호출을 허용
        resp.setHeader("Access-Control-Allow-Methods", "*");   // 메서드의 POST, GET, OPTIONS 요청만 허용
        resp.setHeader("Access-Control-Allow-Headers", "*"); //
        resp.setHeader("Access-Control-Allow-Credentials", "true"); // 브라우저에서 저장되어지는 쿠키를 허용

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
