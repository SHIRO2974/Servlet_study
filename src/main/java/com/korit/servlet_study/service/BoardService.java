package com.korit.servlet_study.service;

import com.korit.servlet_study.dao.BoardDao;
import com.korit.servlet_study.entity.Board;

public class BoardService {

    private BoardDao boardDao;
    private static BoardService boardService;

    private BoardService() {
        boardDao = BoardDao.getInstance();
    }

    public static BoardService getInstance() {

        if (boardService == null) {

            boardService = new BoardService();
        }
        return boardService;
    }

    public Board addBoard(Board board) {

        boardDao.saveTitle(board.getTitle());
    }
}
