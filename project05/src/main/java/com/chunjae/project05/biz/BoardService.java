package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Board;
import com.chunjae.project05.entity.BoardMgn;
import com.chunjae.project05.entity.BoardVO;
import com.chunjae.project05.util.BoardPage;
import com.chunjae.project05.util.Page;

import java.util.List;

public interface BoardService {

    public List<BoardMgn> listBoardMgnForHeader() throws Exception;
    public List<BoardMgn> listBoardMgn(Page page) throws Exception;
    public int countBoardMgn(Page page) throws Exception;
    public BoardMgn getBoardMgn(int bmNo) throws Exception;
    public int boardMgnInsert(BoardMgn boardMgn) throws Exception;
    public int boardMgnUpdate(BoardMgn boardMgn) throws Exception;
    public int boardMgnDelete(int bmNo) throws Exception;
    public List<BoardVO> boardList(BoardPage page) throws Exception;
    public int boardCount(BoardPage page) throws Exception;
    public List<BoardVO> boardListForAdmin(int bmNo) throws Exception;
    public BoardVO boardGet(boolean hasCookie, int bno, Long loginId) throws Exception;
    public BoardVO boardInsert(Board board) throws Exception;
    public int boardUpdate(Board board) throws Exception;
    public int qnaUpdate(Board board) throws Exception;
    public int boardDelete(int bno) throws Exception;

}