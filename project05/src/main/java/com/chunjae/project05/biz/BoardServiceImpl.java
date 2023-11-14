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
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<BoardMgn> listBoardMgnForHeader() throws Exception {
        return boardMapper.listBoardMgnForHeader();
    }

    @Override
    public List<BoardMgn> listBoardMgn(Page page) throws Exception {
        return boardMapper.listBoardMgn(page);
    }

    @Override
    public int countBoardMgn(Page page) throws Exception {
        return boardMapper.countBoardMgn(page);
    }

    @Override
    public BoardMgn getBoardMgn(int bmNo) throws Exception {
        return boardMapper.getBoardMgn(bmNo);
    }

    @Override
    public int boardMgnInsert(BoardMgn boardMgn) throws Exception {
        return boardMapper.boardMgnInsert(boardMgn);
    }

    @Override
    public int boardMgnUpdate(BoardMgn boardMgn) throws Exception {
        return boardMapper.boardMgnUpdate(boardMgn);
    }

    @Override
    public int boardMgnDelete(int bmNo) throws Exception {
        return boardMapper.boardMgnDelete(bmNo);
    }

    @Override
    public List<BoardVO> boardList(BoardPage page) throws Exception {
        return boardMapper.boardList(page);
    }

    @Override
    public int boardCount(BoardPage page) throws Exception {
        return boardMapper.boardCount(page);
    }

    @Override
    public List<BoardVO> boardListForAdmin(int bmNo) throws Exception {
        return boardMapper.boardListForAdmin(bmNo);
    }

    @Override
    public BoardVO boardGet(boolean hasCookie, int bno, Long userId) throws Exception {
        BoardVO boardVO = boardMapper.boardGet(bno);
        if(!userId.equals(boardVO.getAuthor()) && !userId.equals(1L)) {
            if(!hasCookie){
                boardMapper.boardVisitedUpdate(bno);
                boardVO.setVisited(boardVO.getVisited() + 1);
            }
        }
        return boardVO;
    }

    @Override
    public BoardVO boardInsert(Board board) throws Exception {
        boardMapper.boardInsert(board);
        return boardMapper.boardGetLast();
    }

    @Override
    public int boardUpdate(Board board) throws Exception {
        return boardMapper.boardUpdate(board);
    }

    @Override
    public int qnaUpdate(Board board) throws Exception {
        return boardMapper.qnaUpdate(board);
    }

    @Override
    public int boardDelete(int bno) throws Exception {
        return boardMapper.boardDelete(bno);
    }

}