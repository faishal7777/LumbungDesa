package com.runupstdio.lumbungdesa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenjualanData {
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
    private String priceTotals;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id_buyer")
    @Expose
    private String idBuyer;
    @SerializedName("id_payment")
    @Expose
    private String idPayment;
    @SerializedName("price_total")
    @Expose
    private String priceTotal;
    @SerializedName("checked_out")
    @Expose
    private String checkedOut;
    @SerializedName("shipped")
    @Expose
    private String shipped;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("delivered")
    @Expose
    private String delivered;
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
    @SerializedName("ava_product")
    @Expose
    private String avaProduct;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;

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

    public String getPriceTotals() {
        return priceTotals;
    }

    public void setPriceTotals(String priceTotals) {
        this.priceTotals = priceTotals;
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

    public String getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(String idBuyer) {
        this.idBuyer = idBuyer;
    }

    public String getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(String idPayment) {
        this.idPayment = idPayment;
    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
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

    public String getAvaProduct() {
        return avaProduct;
    }

    public void setAvaProduct(String avaProduct) {
        this.avaProduct = avaProduct;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }
}
