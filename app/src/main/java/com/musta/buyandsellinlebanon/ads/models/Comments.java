package com.musta.buyandsellinlebanon.ads.models;

import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("id")
    String id;
    @SerializedName("client_id")
    String client_id;
    @SerializedName("ad_id")
    String ad_id;
    @SerializedName("text")
    String text;
    @SerializedName("creation_date")
    String creation_date;
    @SerializedName("username")
    String username;
    @SerializedName("name")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
