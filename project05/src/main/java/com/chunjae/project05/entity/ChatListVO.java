package com.chunjae.project05.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatListVO {

    private Long chatId;
    private String sendDate;
    private String message;
    private boolean readYn;
    private Long roomId;
    private Long senderId;
    private String userName;

}