package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.Board;
import com.chunjae.project05.entity.BoardMgn;
import com.chunjae.project05.entity.BoardVO;
import com.chunjae.project05.util.BoardPage;
import com.chunjae.project05.util.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardMgn> listBoardMgnForHeader() throws Exception;
    List<BoardMgn> listBoardMgn(Page page) throws Exception;
    int countBoardMgn(Page page) throws Exception;
    BoardMgn getBoardMgn(int bmNo) throws Exception;
    int boardMgnInsert(BoardMgn boardMgn) throws Exception;
    int boardMgnUpdate(BoardMgn boardMgn) throws Exception;
    int boardMgnDelete(int bmNo) throws Exception;
    List<BoardVO> boardList(BoardPage page) throws Exception;
    int boardCount(BoardPage page) throws Exception;
    List<BoardVO> boardListForAdmin(int bmNo) throws Exception;
    BoardVO boardGet(int bno) throws Exception;
    BoardVO boardGetLast() throws Exception;
    void boardInsert(Board board) throws Exception;
    int boardUpdate(Board board) throws Exception;
    int qnaUpdate(Board board) throws Exception;
    void boardVisitedUpdate(int bno) throws Exception;
    int boardDelete(int bno) throws Exception;

}