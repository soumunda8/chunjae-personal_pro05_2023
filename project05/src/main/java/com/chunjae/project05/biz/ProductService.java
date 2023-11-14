package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> productList() throws Exception;

    public Product productListByUser(Long userId) throws Exception;

}