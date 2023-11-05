package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMgn {

    private int bmNo;
    private String boardName;
    private int aboutAuth;
    private boolean commentUse = false;
    private boolean fileUse = false;

}