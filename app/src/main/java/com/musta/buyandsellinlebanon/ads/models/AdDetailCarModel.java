package com.musta.buyandsellinlebanon.ads.models;

import com.google.gson.annotations.SerializedName;

public class AdDetailCarModel {

    @SerializedName("id")
    String id;
    @SerializedName("car")
    String car;
    @SerializedName("car_ar")
    String car_ar;
    @SerializedName("car_sub")
    String car_sub;
    @SerializedName("car_sub_ar")
    String car_sub_ar;
    @SerializedName("distance_covered")
    String distance_covered;
    @SerializedName("transmission_type")
    String transmission_type;
    @SerializedName("year")
    String year;
    @SerializedName("is_sale")
    String is_sale;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCar_ar() {
        return car_ar;
    }

    public void setCar_ar(String car_ar) {
        this.car_ar = car_ar;
    }

    public String getCar_sub() {
        return car_sub;
    }

    public void setCar_sub(String car_sub) {
        this.car_sub = car_sub;
    }

    public String getCar_sub_ar() {
        return car_sub_ar;
    }

    public void setCar_sub_ar(String car_sub_ar) {
        this.car_sub_ar = car_sub_ar;
    }

    public String getDistance_covered() {
        return distance_covered;
    }

    public void setDistance_covered(String distance_covered) {
        this.distance_covered = distance_covered;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public void setTransmission_type(String transmission_type) {
        this.transmission_type = transmission_type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIs_sale() {
        return is_sale;
    }

    public void setIs_sale(String is_sale) {
        this.is_sale = is_sale;
    }
}
