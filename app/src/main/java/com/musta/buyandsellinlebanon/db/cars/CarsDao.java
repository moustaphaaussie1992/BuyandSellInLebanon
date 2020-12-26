package com.musta.buyandsellinlebanon.db.cars;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cars cars);

    @Query("DELETE FROM cars")
    void deleteAll();

    @Query("SELECT * from cars")
    LiveData<List<Cars>> getLiveDataCars();

}

