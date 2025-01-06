package com.korit.servlet_study.servlet;

import com.korit.servlet_study.entity.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        List<User> users = new ArrayList<>();
        users.add(new User("aaa", "1111", "aaaaaa", "aaaa@gmail.com"));
        users.add(new User("bbb", "1111", "bbbbbb", "bbbb@gmail.com"));
        users.add(new User("ccc", "1111", "cccccc", "cccc@gmail.com"));
        users.add(new User("ddd", "1111", "dddddd", "dddd@gmail.com"));

        // 데이터를 ServletContext 에 저장하여 모든 요청에서 공유 가능.
        config.getServletContext().setAttribute("users", users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 사용자가 입력한 검색 값을 가져온다
        String searchValue = req.getParameter("searchValue");
        ServletContext servletContext = req.getServletContext();

        // ServletContext 에서 사용자 데이터를 가져온다
        List<User> users = (List<User>) servletContext.getAttribute("users");

        // 검색 값이 존재한다면
        if (searchValue != null) {

            // 값이 공백이 아니라면
            if (!searchValue.isBlank()) {

                // 사용자 목록을 필터링
                req.setAttribute("users", users.stream()
                        .filter( user -> user.getUsername()
                                .contains(searchValue))
                        .collect(Collectors.toList()));
            }
        }
        req.getRequestDispatcher("/WEB-INF/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 폼 데이터를 기반으로 새 사용자 객체를 생성
        User user = User.builder()
                .username(req.getParameter("userName"))
                .password(req.getParameter("password"))
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .build();

        // 기존 사용자 데이터를 가져옴
        ServletContext servletContext = req.getServletContext();
        List<User> users = (List<User>) servletContext.getAttribute("users");

        if (users.stream().filter(u -> u.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList()).size() > 0) {
             resp.setContentType("text/html");
             resp.getWriter().println("<script>"
             + "alert('이미 존재하는 사용자 이름입니다.');"
             + "history.back();"
             + "</script>");
             return;
        }
        users.add(user);

        resp.sendRedirect("http://localhost:8080/servlet_study_war/user");
    }
}
