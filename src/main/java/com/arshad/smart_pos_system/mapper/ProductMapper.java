package com.arshad.smart_pos_system.mapper;

import com.arshad.smart_pos_system.dto.ProductDto;
import com.arshad.smart_pos_system.model.Product;
import com.arshad.smart_pos_system.model.Store;

public class ProductMapper {

    // Product -> ProductDto
    public static ProductDto productToProductDto(Product product) {

        if (product == null) {
            return null;
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .mrp(product.getMrp())
                .image(product.getImage())
                .brand(product.getBrand())
                .sku(product.getSku())
                .description(product.getDescription())
                .sellingPrice(product.getSellingPrice())
                .storeId(product.getStore() != null ? product.getStore().getId() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    // ProductDto -> Product
    public static  Product productDtoToEntity(ProductDto productDto, Store store) {

        if (productDto == null) {
            return null;
        }

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .sku(productDto.getSku())
                .brand(productDto.getBrand())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .image(productDto.getImage())
                .store(store)
                .createdAt(productDto.getCreatedAt())
                .updatedAt(productDto.getUpdatedAt())
                .build();
    }
    public static void updateProductFromDto(ProductDto dto, Product product)
    {
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        product.setMrp(dto.getMrp());
        product.setSellingPrice(dto.getSellingPrice());
        product.setImage(dto.getImage());
    }
}