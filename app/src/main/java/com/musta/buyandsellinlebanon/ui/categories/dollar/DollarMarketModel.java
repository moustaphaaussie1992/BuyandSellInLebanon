package com.musta.buyandsellinlebanon.ui.categories.dollar;

import com.google.gson.annotations.SerializedName;

public class DollarMarketModel {

    @SerializedName("id")
    String id;
    @SerializedName("amount")
    String amount;
    @SerializedName("price")
    String price;
    @SerializedName("place")
    String place;
    @SerializedName("phone")
    String phone;
    @SerializedName("type")
    String type;
    @SerializedName("creation_date")
    String creation_date;
    @SerializedName("place_en")
    String place_en;
    @SerializedName("place_ar")
    String place_ar;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getPlace_en() {
        return place_en;
    }

    public void setPlace_en(String place_en) {
        this.place_en = place_en;
    }

    public String getPlace_ar() {
        return place_ar;
    }

    public void setPlace_ar(String place_ar) {
        this.place_ar = place_ar;
    }
}
