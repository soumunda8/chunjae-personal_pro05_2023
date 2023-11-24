package com.chunjae.project05.biz;

import com.chunjae.project05.entity.Product;
import com.chunjae.project05.persistence.ProductMapper;
import com.chunjae.project05.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> productList(Page page) throws Exception {
        return productMapper.productList(page);
    }

    @Override
    public int getTotalCount(Page page) throws Exception {
        return productMapper.getTotalCount(page);
    }

    public List<Product> productListForMain() throws Exception {
        return productMapper.productListForMain();
    }

    @Override
    public List<Product> productListByUser(Long userId) throws Exception {
        return productMapper.productListByUser(userId);
    }

    @Override
    public Product productGet(int proNo) throws Exception {
        return productMapper.productGet(proNo);
    }

    @Override
    public Product productGetAll(int proNo, Long userId) throws Exception {
        return productMapper.productGetAll(proNo, userId);
    }
}