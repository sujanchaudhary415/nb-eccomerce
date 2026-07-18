package com.eccomerce.nb_eccomerce.entity;

public class Category {
    private String categoryName;
    private int categoryId;

    public Category(String categoryName, int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
