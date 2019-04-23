package com.runupstdio.lumbungdesa.Model;

public class Tagihan {
    private String productName;
    private String productPrice;
    private String imageProductUrl;
    private String status;

    public Tagihan(String productName, String productPrice, String imageProductUrl, String status) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageProductUrl = imageProductUrl;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
