package com.musta.buyandsellinlebanon.db.cars;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.musta.buyandsellinlebanon.ads.CreateAdActivity;

@Entity(tableName = "cars")
public class Cars {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "car_id")
    private long car_id;
    @ColumnInfo(name = "car_name")
    private String car_name;
    @ColumnInfo(name = "car_name_ar")
    private String car_name_ar;


    public long getCar_id() {
        return car_id;
    }

    public void setCar_id(long car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_name_ar() {
        return car_name_ar;
    }

    public void setCar_name_ar(String car_name_ar) {
        this.car_name_ar = car_name_ar;
    }

    @NonNull
    @Override
    public String toString() {
        if (CreateAdActivity.LANGUAGE.equals("ar")) {
            return car_name_ar;
        } else {
            return car_name;
        }
    }
}
