package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.entity.Author;
import com.korit.servlet_study.entity.Book;
import com.korit.servlet_study.entity.BookCategory;
import com.korit.servlet_study.entity.Publisher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/book")
public class BookRestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Author author = new Author(1, "저자이름");
        Publisher publisher = new Publisher(1, "아무출판사");
        BookCategory bookCategory = new BookCategory(1, "액션");

        Book book = Book.builder()
                .bookName("아무책")
                .isbn("123-456-789")
                .bookImgUrl("www.weqewqr.jpg")
                .author(author)  // Author 객체 추가
                .publisher(publisher)  // Publisher 객체 추가
                .bookCategory(bookCategory)  // BookCategory 객체 추가
                .build();

        String jsonBook = objectMapper.writeValueAsString(book);
        System.out.println(jsonBook);

        resp.setHeader("Access-Control-Allow-Origin", "*"); // 모든 서버에서 호출을 허용
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");   // 메서드의 POST, GET, OPTIONS 요청만 허용
        resp.setHeader("Access-Control-Allow-Headers", "Content-type"); //
        resp.setHeader("Access-Control-Allow-Credentials", "true"); // 브라우저에서 저장되어지는 쿠키를 허용

        resp.setContentType("application/json");
        resp.getWriter().println(jsonBook);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
