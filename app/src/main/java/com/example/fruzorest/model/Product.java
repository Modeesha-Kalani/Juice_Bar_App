package com.example.fruzorest.model;

public class Product {
    private String name;
    private String id;
    private String ingredients;
    private double reg_price;
    private double large_price;
    private String type;

    public Product() {
    }

    public Product(String name, String id, String ingredients, double reg_price, double large_price, String type) {
        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.reg_price = reg_price;
        this.large_price = large_price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getReg_price() {
        return reg_price;
    }

    public void setReg_price(double reg_price) {
        this.reg_price = reg_price;
    }

    public double getLarge_price() {
        return large_price;
    }

    public void setLarge_price(double large_price) {
        this.large_price = large_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
