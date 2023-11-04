package com.chunjae.project05.controller;

import com.chunjae.project05.domain.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/board")
public class BoardCtrl {

    @GetMapping( "/list.do")
    public ModelAndView boardList(Model model){

        ModelAndView modelAndView = new ModelAndView();

        // 페이지 공통 설정
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("auth", auth.getPrincipal());                // 로그인 여부
        modelAndView.addObject("titleName", "공지사항");
        modelAndView.addObject("pageType", "sub");
        modelAndView.addObject("formType", "no");

        modelAndView.setViewName("board/list");
        return modelAndView;
    }

}