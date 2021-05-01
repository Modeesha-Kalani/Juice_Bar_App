package com.example.madjuicebar.model;

public class MostPopular {
    String name;
    String rs;
    String price;
    Integer image;

    public MostPopular(String name, String rs, String price, Integer image) {
        this.name = name;
        this.rs = rs;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
