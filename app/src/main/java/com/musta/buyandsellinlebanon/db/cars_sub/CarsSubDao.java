package com.musta.buyandsellinlebanon.db.cars_sub;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.musta.buyandsellinlebanon.db.cars.Cars;

import java.util.List;

@Dao
public interface CarsSubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CarsSub carsSub);

    @Query("DELETE FROM cars_sub")
    void deleteAll();

    @Query("SELECT * from cars_sub")
    LiveData<List<CarsSub>> getLiveDataCarsSub();

    @Query("SELECT * from cars_sub where car_id = :carId")
    LiveData<List<CarsSub>> getLiveDataCarsSubByCarId(long carId);

}
