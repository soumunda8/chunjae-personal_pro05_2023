package com.chunjae.project05.controller;

import com.chunjae.project05.biz.ProductService;
import com.chunjae.project05.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product/")
public class ProductCtrl {

    @Autowired
    private ProductService productService;

    @GetMapping("list.do")
    public String productHome(Model model) throws Exception {

        List<Product> productList = productService.productList();
        model.addAttribute("productList", productList);

        return "product/list";
    }

}