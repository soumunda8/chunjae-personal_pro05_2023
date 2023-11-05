package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    private int cno;
    private String author;
    private String content;
    private String resDate;
    private int parNo;
    private String userName;

}