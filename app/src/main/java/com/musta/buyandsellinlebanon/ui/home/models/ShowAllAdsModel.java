package com.musta.buyandsellinlebanon.ui.home.models;

import com.google.gson.annotations.SerializedName;

public class ShowAllAdsModel {

    @SerializedName("id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("image")
    String image;
    @SerializedName("price")
    String price;
    @SerializedName("price_unit")
    String price_unit;
    @SerializedName("place_name")
    String place_name;
    @SerializedName("creation_date")
    String creation_date;
    @SerializedName("phone")
    String phone;
    @SerializedName("views")
    String views;
    public ShowAllAdsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
