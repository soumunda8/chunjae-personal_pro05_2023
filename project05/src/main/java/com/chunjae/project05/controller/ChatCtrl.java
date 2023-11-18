package com.chunjae.project05.controller;

import com.chunjae.project05.biz.ChatService;
import com.chunjae.project05.biz.ProductService;
import com.chunjae.project05.biz.UserService;
import com.chunjae.project05.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/")
public class ChatCtrl {

    //private final Chat

    @Autowired
    private final ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/myProChat.do")
    public String roomList(@RequestParam("productId") int productId, Model model, Principal principal) throws Exception {

        String sid = principal != null ? principal.getName() : "";

        if(!sid.equals("")) {
            User user = userService.getUserByLoginId(sid);
            Product product = productService.productListByUser(user.getId());
            if(product == null) {
                
                ChatRoomVO chatRoomVO = chatService.chatRoomAllList(productId, user.getId());
                Long roomId;

                if(chatRoomVO == null) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setProductId(Math.toIntExact(productId));
                    chatRoom.setBuyerId(user.getId());
                    ChatRoomVO room = chatService.createChatRoom(chatRoom);
                    roomId = room.getRoomId();
                } else {
                    roomId = chatRoomVO.getRoomId();
                }

                return "redirect:/chat/getChat.do/" + roomId;
            } else {
                List<ChatRoomVO> roomList = chatService.chatRoomAllListByProduct(productId);
                model.addAttribute("roomList", roomList);
                return "chat/list";
            }
        } else {
            return "redirect:/product/list.do";
        }
    }

    @GetMapping("/getChat.do/{roomId}")
    public String joinRoom(@PathVariable(required = false) Long roomId, Model model, Principal principal) throws Exception {

        String sid = principal != null ? principal.getName() : "";

        if(!sid.equals("")) {
            User user = userService.getUserByLoginId(sid);
            Product product = productService.productListByUser(user.getId());
            ChatRoomVO chatRoomVO = chatService.getRoom(roomId);
            if(product != null || (chatRoomVO != null && chatRoomVO.getBuyerId().equals(user.getId()))) {
                List<ChatListVO> chatList = chatService.getChat(roomId);
                model.addAttribute("roomId", roomId);
                model.addAttribute("chatList", chatList);
                model.addAttribute("userName", user.getUserName());
                return "chat/get";
            }
        }

        return "redirect:/product/list.do";

    }

    @MessageMapping("/{roomId}")
    @SendTo("/chat/getChat.do/{roomId}")
    public ChatListVO chatInsert(@DestinationVariable Long roomId, ChatListVO message, Principal principal) throws Exception {

        String sid = principal.getName();
        User user = userService.getUserByLoginId(sid);

        ChatList chatList = new ChatList();
        chatList.setRoomId(roomId);
        chatList.setMessage(message.getMessage());
        chatList.setSenderId(user.getId());
        int cnt = chatService.insertChatList(chatList);

        return chatService.getChatLast(roomId);
    }

}