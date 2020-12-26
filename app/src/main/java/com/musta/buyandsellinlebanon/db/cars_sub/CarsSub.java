package com.musta.buyandsellinlebanon.db.cars_sub;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.musta.buyandsellinlebanon.ads.CreateAdActivity;

@Entity(tableName = "cars_sub")
public class CarsSub {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "model_id")
    private long model_id;
    @ColumnInfo(name = "model_name")
    private String model_name;
    @ColumnInfo(name = "model_name_ar")
    private String model_name_ar;
    @ColumnInfo(name = "car_id")
    private int car_id;

    public long getModel_id() {
        return model_id;
    }

    public void setModel_id(long model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getModel_name_ar() {
        return model_name_ar;
    }

    public void setModel_name_ar(String model_name_ar) {
        this.model_name_ar = model_name_ar;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    @NonNull
    @Override
    public String toString() {
        if (CreateAdActivity.LANGUAGE.equals("ar")) {
            return model_name_ar;
        } else {
            return model_name;
        }
    }
}
