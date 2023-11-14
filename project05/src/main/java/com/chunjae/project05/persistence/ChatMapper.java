package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.ChatList;
import com.chunjae.project05.entity.ChatListVO;
import com.chunjae.project05.entity.ChatRoom;
import com.chunjae.project05.entity.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    ChatRoomVO chatRoomAllList(int productId, Long buyerId) throws Exception;
    List<ChatRoomVO> chatRoomAllListByProduct(int productId) throws Exception;
    List<ChatRoomVO> chatRoomAllListByBuyerId(Long buyerId) throws Exception;
    ChatRoomVO getRoom(Long roomId) throws Exception;
    ChatRoomVO getChatRoomLast() throws Exception;
    void createChatRoom(ChatRoom chatRoom) throws Exception;

    List<ChatListVO> getChat(Long roomId) throws Exception;
    ChatListVO getChatLast(Long roomId) throws Exception;
    int insertChatList(ChatList chatList) throws Exception;
    int updateChatRead(Long chatId) throws Exception;

}