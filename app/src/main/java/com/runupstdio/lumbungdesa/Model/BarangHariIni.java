package com.runupstdio.lumbungdesa.Model;

public class BarangHariIni {

    private int productId;
    private String productName;
    private String productPrice;
    private String imageProductUrl;

    public BarangHariIni(int productId, String productName, String productPrice, String imageProductUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageProductUrl = imageProductUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
