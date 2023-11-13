package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.ChatRoom;
import com.chunjae.project05.entity.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    List<ChatRoom> chatRoomAllListByProduct(Long productId) throws Exception;
    List<ChatRoom> chatRoomAllListByBuyerId(Long buyerId) throws Exception;
    List<ChatRoom> chatRoomAllListByRoomId(Long roomId) throws Exception;
    int createChatRoom(ChatRoomVO chatRoomVO) throws Exception;

}