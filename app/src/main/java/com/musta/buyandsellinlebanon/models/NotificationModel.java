package com.musta.buyandsellinlebanon.models;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("client_id")
    String client_id;
    @SerializedName("from_client")
    String from_client;
    @SerializedName("type")
    String type;
    @SerializedName("ad_id")
    String ad_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getFrom_client() {
        return from_client;
    }

    public void setFrom_client(String from_client) {
        this.from_client = from_client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }
}
