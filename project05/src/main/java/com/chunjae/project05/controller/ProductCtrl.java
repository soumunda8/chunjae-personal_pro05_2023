package com.chunjae.project05.controller;

import com.chunjae.project05.biz.ProductService;
import com.chunjae.project05.entity.Product;
import com.chunjae.project05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/product/")
public class ProductCtrl {

    @Autowired
    private ProductService productService;

    @GetMapping("list.do")
    public String productHome(Model model, HttpServletRequest request) throws Exception {

        int curPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        String type = request.getParameter("type") != null ? request.getParameter("type") : "";
        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";

        Page page = new Page();
        page.setSearchType(type);
        page.setSearchKeyword(keyword);

        int total = productService.getTotalCount(page);
        page.setPostCount(8);
        page.makeBlock(curPage, total);
        page.makeLastPageNum(total);
        page.makePostStart(curPage, total);

        model.addAttribute("page", page);

        List<Product> productList = productService.productList(page);
        model.addAttribute("productList", productList);

        return "product/list";
    }

    @GetMapping("detail.do")
    public String productDetail(@RequestParam("no") int proNo, Model model) throws Exception {

        Product product = productService.productGet(proNo);
        model.addAttribute("product", product);

        return "product/get";
    }

}