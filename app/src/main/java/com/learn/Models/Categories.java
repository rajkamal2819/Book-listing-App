package com.learn.Models;

public class Categories {

    private int categoryImage;
    private String categoryName;

    public Categories(int categoryImage, String categoryName) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
    }

    public int getCategoriesImage() {
        return categoryImage;
    }

    public void setCategoriesImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoriesName() {
        return categoryName;
    }

    public void setCategoriesName(String categoryName) {
        this.categoryName = categoryName;
    }

}
