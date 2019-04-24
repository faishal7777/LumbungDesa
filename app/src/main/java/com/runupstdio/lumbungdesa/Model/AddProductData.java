package com.runupstdio.lumbungdesa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddProductData {
    @SerializedName("product")
    @Expose
    private AddProductDataProduct product;
    @SerializedName("images_url")
    @Expose
    private List<String> imagesUrl = null;

    public AddProductDataProduct getProduct() {
        return product;
    }

    public void setProduct(AddProductDataProduct product) {
        this.product = product;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

}
