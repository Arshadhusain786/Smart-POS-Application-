package com.arshad.smart_pos_system.service;

import com.arshad.smart_pos_system.dto.ProductDto;
import com.arshad.smart_pos_system.model.Product;
import com.arshad.smart_pos_system.model.User;

import java.util.List;

public interface ProductService
{

    ProductDto createProduct(ProductDto productDto, User user);

    ProductDto updateProduct(ProductDto productDto, Long id, User user);

    void deleteProduct(Long id);

    List<ProductDto> findAllProducts();

    Product findProductById(Long id);

    List<ProductDto> searchByKeyword(Long storeId, String keyword);

    List<ProductDto> findAllProductsByName(String name);

    List<ProductDto> findProductsByStoreId(Long storeId);

    List<ProductDto> findAllProductsByStoreIdAndName(Long storeId, String name);

}