package com.chunjae.project05.controller;

import com.chunjae.project05.biz.ProductService;
import com.chunjae.project05.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeCtrl {

    @Autowired
    private ProductService productService;

    @GetMapping( "/")
    public String home(Model model) throws Exception {

        List<Product> productList = productService.productListForMain();
        model.addAttribute("productList", productList);

        // 페이지 공통 설정
        model.addAttribute("titleName", "중고 서적");
        model.addAttribute("pageType", "main");

        return "index";
    }

}