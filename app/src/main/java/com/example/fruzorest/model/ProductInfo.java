package com.example.fruzorest.model;

import android.content.Context;
import android.net.Uri;

public class ProductInfo {
    private Product product;
    private Uri uri; //download images from firebase storage

    public ProductInfo() {
    }

    public ProductInfo(Product product,  Uri uri) {
        this.product = product;
        this.uri = uri;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }



    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
