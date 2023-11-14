package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Product;
import com.chunjae.project05.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> productList() throws Exception {
        return productMapper.productList();
    }

    @Override
    public Product productListByUser(Long userId) throws Exception {
        return productMapper.productListByUser(userId);
    }

}