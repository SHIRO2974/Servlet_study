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
        users.add(new User("aaa", "1111", "aaaaaaaa", "aaaa@gmail.com"));
        users.add(new User("bbb", "1111", "bbbbbbbb", "bbbb@gmail.com"));
        users.add(new User("ccc", "1111", "cccccccc", "cccc@gmail.com"));
        users.add( new User("dddd", "1111", "ddddddd", "dddd@gmail.com"));

        config.getServletContext().setAttribute("users", users);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchValue = req.getParameter("searchValue");
        ServletContext servletContext = req.getServletContext();
        List<User> users = (List<User>) servletContext.getAttribute("users");

        if (searchValue != null) {

            if (searchValue.isBlank()) {
                req.setAttribute("users", users.stream()
                        .filter(u -> u.getUsername()
                                .contains(searchValue))
                        .collect(Collectors.toList()));
            }
        }
        req.getRequestDispatcher("/WEB-INF/user.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .build();
    }
}
