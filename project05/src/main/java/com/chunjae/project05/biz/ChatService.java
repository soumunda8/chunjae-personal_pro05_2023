package com.chunjae.project05.biz;

import com.chunjae.project05.entity.ChatRoom;
import com.chunjae.project05.entity.ChatRoomVO;
import com.chunjae.project05.persistence.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Autowired
    private final ChatMapper chatMapper;

    public List<ChatRoom> chatRoomAllListByProduct(Long productId) throws Exception {
        return chatMapper.chatRoomAllListByProduct(productId);
    }

    public List<ChatRoom> chatRoomAllListByBuyerId(Long buyerId) throws Exception {
        return chatMapper.chatRoomAllListByBuyerId(buyerId);
    }

    public List<ChatRoom> chatRoomAllListByRoomId(Long roomId) throws Exception {
        return chatMapper.chatRoomAllListByRoomId(roomId);
    }

    public int createChatRoom(ChatRoomVO chatRoomVO) throws Exception {
        return chatMapper.createChatRoom(chatRoomVO);
    }

}