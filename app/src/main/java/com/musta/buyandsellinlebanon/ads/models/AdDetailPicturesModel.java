package com.musta.buyandsellinlebanon.ads.models;

import com.google.gson.annotations.SerializedName;

public class AdDetailPicturesModel {

    @SerializedName("id")
    String id;
    @SerializedName("ads_id")
    String ads_id;
    @SerializedName("ads_type")
    String ads_type;
    @SerializedName("picture_name")
    String picture_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAds_id() {
        return ads_id;
    }

    public void setAds_id(String ads_id) {
        this.ads_id = ads_id;
    }

    public String getAds_type() {
        return ads_type;
    }

    public void setAds_type(String ads_type) {
        this.ads_type = ads_type;
    }

    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(String picture_name) {
        this.picture_name = picture_name;
    }
}
