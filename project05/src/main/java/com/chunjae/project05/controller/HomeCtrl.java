package com.chunjae.project05.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeCtrl {

    @GetMapping( "/")
    public String home(Model model){

        // 페이지 공통 설정
        model.addAttribute("titleName", "중고 서적");
        model.addAttribute("pageType", "main");

        return "index";
    }

}