package com.example.foro.models;

import java.io.Serializable;

public class Category implements Serializable {

    private int idCategory;
    private String category;

    public Category() {
    }

    public Category(int idCategory, String category) {
        this.idCategory = idCategory;
        this.category = category;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
