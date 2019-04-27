package com.runupstdio.lumbungdesa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenjualanDataProduct {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_category")
    @Expose
    private String idCategory;
    @SerializedName("id_seller")
    @Expose
    private String idSeller;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_stok")
    @Expose
    private String productStok;
    @SerializedName("ava_product")
    @Expose
    private String avaProduct;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStok() {
        return productStok;
    }

    public void setProductStok(String productStok) {
        this.productStok = productStok;
    }

    public String getAvaProduct() {
        return avaProduct;
    }

    public void setAvaProduct(String avaProduct) {
        this.avaProduct = avaProduct;
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

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }
}
