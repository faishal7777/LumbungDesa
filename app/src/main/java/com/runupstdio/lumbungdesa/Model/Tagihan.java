package com.runupstdio.lumbungdesa.Model;

import java.util.List;

public class Tagihan {
    private String productName, totalPrice, productStatus;
    private List<String> imageProductUrl;

    public Tagihan(String prodName, String productPrice, List<String> prodUrl, String stats){
        this.productName = prodName;
        this.totalPrice = productPrice;
        this.imageProductUrl = prodUrl;
        this.productStatus = stats;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<String> getProductUrl() {
        return imageProductUrl;
    }

    public void setProductUrl(List<String> prodUrl) {
        this.imageProductUrl = prodUrl;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String stats) {
        this.productStatus = stats;
    }
}
