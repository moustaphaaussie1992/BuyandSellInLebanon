package com.musta.buyandsellinlebanon.models;

import com.google.gson.annotations.SerializedName;

public class AdModel {

    @SerializedName("id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("creation_date")
    String creation_date;
    @SerializedName("description")
    String description;
    @SerializedName("phone")
    String phone;
    @SerializedName("price_unit")
    String price_unit;
    @SerializedName("price")
    String price;
    @SerializedName("place_name_en")
    String place_name_en;
    @SerializedName("place_name_ar")
    String place_name_ar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlace_name_en() {
        return place_name_en;
    }

    public void setPlace_name_en(String place_name_en) {
        this.place_name_en = place_name_en;
    }

    public String getPlace_name_ar() {
        return place_name_ar;
    }

    public void setPlace_name_ar(String place_name_ar) {
        this.place_name_ar = place_name_ar;
    }
}
