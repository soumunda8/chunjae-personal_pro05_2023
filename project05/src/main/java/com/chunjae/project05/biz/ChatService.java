package com.chunjae.project05.biz;

import com.chunjae.project05.entity.ChatList;
import com.chunjae.project05.entity.ChatListVO;
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

    public ChatRoomVO chatRoomAllList(int productId, Long buyerId) throws Exception {
        return chatMapper.chatRoomAllList(productId, buyerId);
    }

    public List<ChatRoomVO> chatRoomAllListByProduct(int productId) throws Exception {
        return chatMapper.chatRoomAllListByProduct(productId);
    }

    public List<ChatRoomVO> chatRoomAllListByBuyerId(Long buyerId) throws Exception {
        return chatMapper.chatRoomAllListByBuyerId(buyerId);
    }

    public ChatRoomVO getRoom(Long roomId) throws Exception {
        return chatMapper.getRoom(roomId);
    }

    public ChatRoomVO createChatRoom(ChatRoom chatRoom) throws Exception {
        chatMapper.createChatRoom(chatRoom);
        return chatMapper.getChatRoomLast();
    }


    public List<ChatListVO> getChat(Long roomId) throws Exception {
        return chatMapper.getChat(roomId);
    }

    public ChatListVO getChatLast(Long roomId) throws Exception {
        return chatMapper.getChatLast(roomId);
    }

    public int insertChatList(ChatList chatList) throws Exception {
        return chatMapper.insertChatList(chatList);
    }
    public int updateChatRead(Long chatId) throws Exception {
        return chatMapper.updateChatRead(chatId);
    }

}