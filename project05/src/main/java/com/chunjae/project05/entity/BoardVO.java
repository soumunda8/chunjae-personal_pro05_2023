package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {

    private int bno;
    private int bmNo;
    private String title;
    private String content;
    private String author;
    private String resDate;
    private int visited;
    private String boardName;
    private String userName;
    private int aboutAuth;
    private boolean commentUse;
    private boolean fileUse;

}