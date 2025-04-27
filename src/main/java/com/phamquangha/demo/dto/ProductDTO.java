package com.phamquangha.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private double price;
    private int quantity;
    private String image;
    private Long categoryId; // id của Category
    private Long brandId; // id của Brand

    // getters and setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    // Các getter và setter khác cho các trường còn lại
}
