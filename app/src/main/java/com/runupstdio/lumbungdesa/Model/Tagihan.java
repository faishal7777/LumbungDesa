package com.runupstdio.lumbungdesa.Model;

import java.util.List;

public class Tagihan {
    private String productName, totalPrice, productStatus;
    private List<String> imageProductUrl;
    private int idTrx, paymentMethod;

    public Tagihan(int idtrx, String prodName, String productPrice, List<String> prodUrl, String stats, int payment){
        this.idTrx = idtrx;
        this.productName = prodName;
        this.totalPrice = productPrice;
        this.imageProductUrl = prodUrl;
        this.productStatus = stats;
        this.paymentMethod = payment;
    }

    public int getIdTrx() {
        return idTrx;
    }

    public void setIdTrx(int id) {
        this.idTrx = id;
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

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int payment) {
        this.paymentMethod = payment;
    }
}
