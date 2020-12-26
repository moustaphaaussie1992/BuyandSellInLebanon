package com.musta.buyandsellinlebanon.db.places;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Places places);

    @Query("DELETE FROM places")
    void deleteAll();

    @Query("SELECT * from places")
    LiveData<List<Places>> getLiveDataPlaces();

}

