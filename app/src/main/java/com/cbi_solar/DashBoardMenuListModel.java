package com.cbi_solar;

import com.cbi_solar.helper.ApiContants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashBoardMenuListModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("price")
    @Expose
    private String price;

    public String getMrp() {
        return "Mrp: ₹ "+mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("product_img")
    @Expose
    private String product_img;
    @SerializedName("isactive")
    @Expose
    private String isactive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getModel() {
        return "Model: "+model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return "Price: ₹ "+price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return "Discount: "+discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProduct_img() {
        return ApiContants.PREF_ImageUrl +product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }
}