package com.korit.servlet_study.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/board")
public class BoardRestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // BufferedReader 를 사용한 후 반드시 해당 리소스를 정리하기 위해 try 사용
        try (BufferedReader bufferedReader = req.getReader()) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {    // 읽을 수 있는 다음라인이 있을경우

                System.out.println(line);   // line 프린트
            }
        }
    }

}
