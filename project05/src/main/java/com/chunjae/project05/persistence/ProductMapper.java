package com.chunjae.project05.persistence;

import com.chunjae.project05.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> productList() throws Exception;

    Product productListByUser(Long userId) throws Exception;

}