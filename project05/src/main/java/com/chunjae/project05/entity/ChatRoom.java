package com.chunjae.project05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private Long roomId;
    private Integer productId;
    private Long buyerId;
    private String regDate;

}