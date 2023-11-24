package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.Product;
import com.chunjae.project05.util.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> productList(Page page) throws Exception;
    int getTotalCount(Page page) throws Exception;
    List<Product> productListForMain() throws Exception;
    List<Product> productListByUser(Long userId) throws Exception;
    Product productGet(int proNo) throws Exception;
    Product productGetAll(int proNo, Long userId) throws Exception;

}