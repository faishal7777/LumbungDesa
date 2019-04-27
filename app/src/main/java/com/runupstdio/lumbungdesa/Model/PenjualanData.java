package com.runupstdio.lumbungdesa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PenjualanData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_buyer")
    @Expose
    private String idBuyer;
    @SerializedName("id_payment")
    @Expose
    private String idPayment;
    @SerializedName("price_total")
    @Expose
    private String priceTotal;
    @SerializedName("price_unique")
    @Expose
    private String priceUnique;
    @SerializedName("checked_out")
    @Expose
    private String checkedOut;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("shipped")
    @Expose
    private String shipped;
    @SerializedName("delivered")
    @Expose
    private String delivered;
    @SerializedName("cencelled")
    @Expose
    private String cencelled;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("products")
    @Expose
    private List<PenjualanDataProduct> products = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPriceUnique() {
        return priceUnique;
    }

    public void setPriceUnique(String priceUnique) {
        this.priceUnique = priceUnique;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getCencelled() {
        return cencelled;
    }

    public void setCencelled(String cencelled) {
        this.cencelled = cencelled;
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

    public List<PenjualanDataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PenjualanDataProduct> products) {
        this.products = products;
    }
}
