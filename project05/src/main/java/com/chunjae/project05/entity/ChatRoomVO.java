package com.chunjae.project05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomVO {

    private Long roomId;
    private Integer productId;
    private String productName = "";
    private String regDate;
    private Long buyerId;
    private String userName;
    private int active;

}