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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/")
public class ChatCtrl {

    @Autowired
    private final ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/chat.do")
    public String chatHome(@RequestParam("no") int productId, Model model, Principal principal) throws Exception {

        if(principal != null) {
            String sid = principal.getName();
            User user = userService.getUserByLoginId(sid);
            Product product = productService.productGetAll(productId, user.getId());

            if(product != null){
                return "redirect:/chat/myProList.do?no=" + productId;
            } else {
                ChatRoomVO chatRoomVO = chatService.chatRoomAllList(productId, user.getId());
                Long roomId;
                if(chatRoomVO == null) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setProductId(productId);
                    chatRoom.setBuyerId(user.getId());
                    ChatRoomVO room = chatService.createChatRoom(chatRoom);
                    roomId = room.getRoomId();
                } else {
                    roomId = chatRoomVO.getRoomId();
                }
                return "redirect:/chat/getChat.do/" + roomId;
            }
        } else {
            return "redirect:/product/list.do";
        }
    }

    @GetMapping("/myProList.do")
    public String roomList(@RequestParam("no") int productId, Model model, Principal principal) throws Exception {
        String sid = principal != null ? principal.getName() : "";
        Product product = productService.productGet(productId);
        User user = userService.getUserByLoginId(sid);

        if(!sid.equals("") && user.getId().equals(product.getUserId())) {

            List<ChatRoomVO> chatRoomList = chatService.chatRoomAllListByProduct(productId);
            model.addAttribute("chatRoomList", chatRoomList);

            String productName = product.getTitle();

            model.addAttribute("myChat", false);
            model.addAttribute("productName", productName);
            return "chat/list";
        } else {
            return "redirect:/product/list.do";
        }
    }

    @GetMapping("/myChatList.do")
    public String myRoomList(Model model, Principal principal, HttpServletRequest request) throws Exception {
        String urlPath = request.getHeader("referer");

        if(principal != null) {
            String sid = principal.getName();
            User user = userService.getUserByLoginId(sid);

            List<ChatRoomVO> chatRoomList = new ArrayList<>();

            List<Product> productList = productService.productListByUser(user.getId());
            for(Product product : productList) {
                if(product.getUserId().equals(user.getId())) {
                    List<ChatRoomVO> productChat = chatService.chatRoomAllListByProduct(product.getProNo());
                    if(productChat != null) {
                        chatRoomList.addAll(productChat);
                    }
                }
            }

            List<ChatRoomVO> buyerList = chatService.chatRoomAllListByBuyerId(user.getId());
            if(buyerList != null) {
                chatRoomList.addAll(buyerList);
            }

            model.addAttribute("chatRoomList", chatRoomList);
            model.addAttribute("myChat", true);
            return "chat/list";
        } else {
            return "redirect:" + urlPath;
        }
    }

    @GetMapping("/getChat.do/{roomId}")
    public String joinRoom(@PathVariable(required = false) Long roomId, Model model, Principal principal) throws Exception {

        String sid = principal != null ? principal.getName() : "";

        if(!sid.equals("")) {
            ChatRoomVO chatRoomVO = chatService.getRoom(roomId);
            User user = userService.getUserByLoginId(sid);
            Product product = productService.productGet(chatRoomVO.getProductId());
            if(product != null || (chatRoomVO != null && chatRoomVO.getBuyerId().equals(user.getId()))) {
                List<ChatListVO> chatList = chatService.getChat(roomId);
                model.addAttribute("roomId", roomId);
                model.addAttribute("chatList", chatList);
                model.addAttribute("chatRoomVO", chatRoomVO);
                model.addAttribute("userName", user.getUserName());
                model.addAttribute("userId", user.getId());
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