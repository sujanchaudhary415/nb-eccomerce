package com.eccomerce.nb_eccomerce.entity;

public class Category {
    private String categoryName;
    private Long categoryId;

    public Category() {
    }

    public Category(String categoryName, long categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
