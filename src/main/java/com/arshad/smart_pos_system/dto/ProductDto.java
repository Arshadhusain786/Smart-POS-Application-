package com.arshad.smart_pos_system.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder

public class ProductDto
{

    private Long id;

    private String name;

    private String sku;

    private String description;

    private Double mrp;

    private Double sellingPrice;

    private String image;

    private String brand;

    private String category;

    private Long storeId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
