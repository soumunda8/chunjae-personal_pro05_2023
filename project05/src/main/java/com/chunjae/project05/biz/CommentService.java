package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Comment;
import com.chunjae.project05.entity.CommentVO;

import java.util.List;

public interface CommentService {

    public List<CommentVO> commentList(int parNo) throws Exception;
    public CommentVO comment(int cno) throws Exception;
    public CommentVO commentInsert(Comment comment) throws Exception;
    public int commentUpdate(Comment comment) throws Exception;
    public int commentDelete(int cno) throws Exception;
    public int commentDeleteAll(int parNo) throws Exception;

}