package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Board;
import com.chunjae.project05.entity.BoardMgn;
import com.chunjae.project05.entity.BoardVO;
import com.chunjae.project05.persistence.BoardMapper;
import com.chunjae.project05.util.BoardPage;
import com.chunjae.project05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardMgn> listBoardMgnForHeader() throws Exception {
        return boardMapper.listBoardMgnForHeader();
    }

    public List<BoardMgn> listBoardMgn(Page page) throws Exception {
        return boardMapper.listBoardMgn(page);
    }

    public int countBoardMgn(Page page) throws Exception {
        return boardMapper.countBoardMgn(page);
    }

    public BoardMgn getBoardMgn(int bmNo) throws Exception {
        return boardMapper.getBoardMgn(bmNo);
    }

    public int boardMgnInsert(BoardMgn boardMgn) throws Exception {
        return boardMapper.boardMgnInsert(boardMgn);
    }

    public int boardMgnUpdate(BoardMgn boardMgn) throws Exception {
        return boardMapper.boardMgnUpdate(boardMgn);
    }

    public int boardMgnDelete(int bmNo) throws Exception {
        return boardMapper.boardMgnDelete(bmNo);
    }

    public List<BoardVO> boardList(BoardPage page) throws Exception {
        return boardMapper.boardList(page);
    }

    public int boardCount(BoardPage page) throws Exception {
        return boardMapper.boardCount(page);
    }

    public List<BoardVO> boardListForAdmin(int bmNo) throws Exception {
        return boardMapper.boardListForAdmin(bmNo);
    }

    public BoardVO boardGet(boolean hasCookie, int bno, String loginId) throws Exception {
        BoardVO boardVO = boardMapper.boardGet(bno);
        if(!loginId.equals(boardVO.getAuthor()) && !loginId.equals("admin")) {
            if(!hasCookie){
                boardMapper.boardVisitedUpdate(bno);
                boardVO.setVisited(boardVO.getVisited() + 1);
            }
        }
        return boardVO;
    }

    public BoardVO boardInsert(Board board) throws Exception {
        boardMapper.boardInsert(board);
        return boardMapper.boardGetLast();
    }

    public int boardUpdate(Board board) throws Exception {
        return boardMapper.boardUpdate(board);
    }

    public int qnaUpdate(Board board) throws Exception {
        return boardMapper.qnaUpdate(board);
    }

    public int boardDelete(int bno) throws Exception {
        return boardMapper.boardDelete(bno);
    }

}