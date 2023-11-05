package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private int bno;
    private int bmNo;
    private String title;
    private String content;
    private String author;
    private String resDate;
    private int visited;

}