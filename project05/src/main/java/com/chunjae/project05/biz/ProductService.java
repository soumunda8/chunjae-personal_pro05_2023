package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Product;
import com.chunjae.project05.util.Page;

import java.util.List;

public interface ProductService {

    public List<Product> productList(Page page) throws Exception;

    public int getTotalCount(Page page) throws Exception;

    public List<Product> productListForMain() throws Exception;

    public List<Product> productListByUser(Long userId) throws Exception;

    public Product productGet(int proNo) throws Exception;

    public Product productGetAll(int proNo, Long userId) throws Exception;

}