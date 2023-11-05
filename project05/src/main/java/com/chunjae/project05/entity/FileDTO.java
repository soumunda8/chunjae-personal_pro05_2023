package com.chunjae.project05.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    private int fno;
    private int parNo;
    private String saveFolder;
    private String originName;
    private String saveName;
    private String fileType;
    private String uploadDate;
    private String toUse;

}