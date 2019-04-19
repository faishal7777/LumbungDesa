package com.runupstdio.lumbungdesa.Model;

public class BarangHariIni {

    private String productName;
    private String productPrice;
    private String imageProductUrl;

    public BarangHariIni(String productName, String productPrice, String imageProductUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageProductUrl = imageProductUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageProductUrl() {
        return imageProductUrl;
    }

    public void setImageProductUrl(String imageProductUrl) {
        this.imageProductUrl = imageProductUrl;
    }
}
