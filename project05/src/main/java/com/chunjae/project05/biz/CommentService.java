package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Comment;
import com.chunjae.project05.entity.CommentVO;
import com.chunjae.project05.persistence.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;


    public List<CommentVO> commentList(int parNo) throws Exception {
        return commentMapper.commentList(parNo);
    }


    public CommentVO comment(int cno) throws Exception {
        return commentMapper.commentGet(cno);
    }


    public CommentVO commentInsert(Comment comment) throws Exception {
        commentMapper.commentInsert(comment);
        return commentMapper.commentLast();
    }


    public int commentUpdate(Comment comment) throws Exception {
        return commentMapper.commentUpdate(comment);
    }


    public int commentDelete(int cno) throws Exception {
        return commentMapper.commentDelete(cno);
    }


    public int commentDeleteAll(int parNo) throws Exception {
        return commentMapper.commentDeleteAll(parNo);
    }

}