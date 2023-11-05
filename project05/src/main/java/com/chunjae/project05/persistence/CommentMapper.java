package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.Comment;
import com.chunjae.project05.entity.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentVO> commentList(int parNO) throws Exception;
    CommentVO commentLast() throws Exception;
    CommentVO commentGet(int cno) throws Exception;
    void commentInsert(Comment comment) throws Exception;
    int commentUpdate(Comment comment) throws Exception;
    int commentDelete(int cno) throws Exception;
    int commentDeleteAll(int parNO) throws Exception;

}