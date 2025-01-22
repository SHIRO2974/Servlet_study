package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.servlet_study.dto.InsertBoardDto;
import com.korit.servlet_study.dto.ResponseDto;
import com.korit.servlet_study.entity.Board;
import com.korit.servlet_study.server_flow.Response;
import com.korit.servlet_study.service.BoardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/board")
public class BoardRestServlet extends HttpServlet {

    private BoardService boardService;

    public BoardRestServlet () {
        boardService = BoardService.getInstance();
    }

    // 클라이언트에서 전달받은 Json 을 받아오기위한 doPost
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder stringBuilder = new StringBuilder();

        // BufferedReader 를 사용한 후 반드시 해당 리소스를 정리하기 위해 try 사용
        // 버퍼에 Json 문자열을 넣어 라인 단위로 끊어서 가지고 온다
        try (BufferedReader bufferedReader = req.getReader()) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {    // 읽을 수 있는 다음라인이 있을경우

                stringBuilder.append(line);   // stringBuilder: Json 문자열을 합쳐서 반환
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();

        // key, value 들을 매칭해서 class 객체로 생성
        InsertBoardDto insertBoardDto = objectMapper.readValue(stringBuilder.toString(), InsertBoardDto.class);

         //  Json 을 java 객체로 변환
        ResponseDto<?> responseDto = boardService.insertBoard(insertBoardDto);
        String responseJson = objectMapper.writeValueAsString(responseDto);

        // 클라이언트에 Json 데이터로 반환하여 응답을 보냄
        resp.setStatus(responseDto.getStatus());
        resp.setContentType("application/json");
        resp.getWriter().println(responseJson);
    }
}
