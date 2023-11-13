package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Comment;
import com.chunjae.project05.entity.CommentVO;
import com.chunjae.project05.persistence.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentVO> commentList(int parNo) throws Exception {
        return commentMapper.commentList(parNo);
    }

    @Override
    public CommentVO comment(int cno) throws Exception {
        return commentMapper.commentGet(cno);
    }

    @Override
    public CommentVO commentInsert(Comment comment) throws Exception {
        commentMapper.commentInsert(comment);
        return commentMapper.commentLast();
    }

    @Override
    public int commentUpdate(Comment comment) throws Exception {
        return commentMapper.commentUpdate(comment);
    }

    @Override
    public int commentDelete(int cno) throws Exception {
        return commentMapper.commentDelete(cno);
    }

    @Override
    public int commentDeleteAll(int parNo) throws Exception {
        return commentMapper.commentDeleteAll(parNo);
    }

}