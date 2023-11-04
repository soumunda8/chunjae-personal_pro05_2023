package com.chunjae.project05.controller;

import com.chunjae.project05.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @GetMapping("/login.do")
    public ModelAndView login(Model model){

        ModelAndView modelAndView = new ModelAndView();

        // 페이지 공통 설정
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("auth", auth.getPrincipal());                // 로그인 여부
        modelAndView.addObject("titleName", "로그인");

        modelAndView.setViewName("user/login");
        return modelAndView;
    }

}