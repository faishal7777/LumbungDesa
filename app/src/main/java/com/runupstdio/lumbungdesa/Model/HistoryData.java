package com.runupstdio.lumbungdesa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryData {
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
    private List<HistoryDataProduct> products = null;

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

    public String getCencelled() {
        return cencelled;
    }

    public void setCencelled(String cencelled) {
        this.cencelled = cencelled;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
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

    public List<HistoryDataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<HistoryDataProduct> products) {
        this.products = products;
    }
}
