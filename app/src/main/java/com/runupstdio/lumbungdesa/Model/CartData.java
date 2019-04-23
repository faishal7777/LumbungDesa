package com.runupstdio.lumbungdesa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("id_product")
    @Expose
    private String idProduct;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("priceTotal")
    @Expose
    private String priceTotal;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_ava")
    @Expose
    private String productAva;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getProductAva() {
        return productAva;
    }

    public void setProductAva(String productAva) {
        this.productAva = productAva;
    }

}
