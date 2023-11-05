package com.chunjae.project05.controller;

import com.chunjae.project05.biz.UserService;
import com.chunjae.project05.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserService userService;

    @GetMapping("/login.do")
    public ModelAndView login(){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("titleName", "로그인");
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    @GetMapping("/registration.do")
    public ModelAndView join(){

        ModelAndView modelAndView = new ModelAndView();
        User user = new User();

        modelAndView.addObject("user", user);
        modelAndView.addObject("titleName", "회원가입");
        modelAndView.setViewName("user/join");
        return modelAndView;
    }

    @PostMapping("/registration.do")
    public ModelAndView joinPro(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByLoginId(user.getLoginId());
        if (userExists != null) {
            bindingResult
                    .rejectValue("loginId", "error.loginId","There is already a user registered with the loginId provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/user/join");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

}