package com.chunjae.project05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int proNo;
    private String title;
    private int proPrice;
    private String content;
    private Long userId;
    private String proActive;
    private String regDate;
    private String location;
    private String proType;
    private String proState;

}