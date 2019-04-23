package com.runupstdio.lumbungdesa.Model;

public class Tagihan {
    private String totalPrice, imageProductUrl1, imageProductUrl2, status;

    public Tagihan(String productPrice, String imageProductUrl1, String imageProductUrl2, String status) {
        this.totalPrice = productPrice;
        this.imageProductUrl1 = imageProductUrl1;
        this.imageProductUrl2 = imageProductUrl2;
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImageProductUrl1() {
        return imageProductUrl1;
    }

    public void setImageProductUrl1(String imageProductUrl1) {
        this.imageProductUrl1 = imageProductUrl1;
    }

    public String getImageProductUrl2() {
        return imageProductUrl2;
    }

    public void setImageProductUrl2(String imageProductUrl2) {
        this.imageProductUrl2 = imageProductUrl2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
