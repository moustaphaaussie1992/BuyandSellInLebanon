package com.musta.buyandsellinlebanon.db.places;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.musta.buyandsellinlebanon.ads.CreateAdActivity;

@Entity(tableName = "places")
public class Places {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "place_id")
    private long place_id;
    @ColumnInfo(name = "place_name")
    private String place_name;
    @ColumnInfo(name = "place_name_ar")
    private String place_name_ar;

    public long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(long place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_name_ar() {
        return place_name_ar;
    }

    public void setPlace_name_ar(String place_name_ar) {
        this.place_name_ar = place_name_ar;
    }

    @NonNull
    @Override
    public String toString() {
        if (CreateAdActivity.LANGUAGE.equals("ar")) {
            return this.place_name_ar;
        } else {
            return place_name;
        }
    }
}
