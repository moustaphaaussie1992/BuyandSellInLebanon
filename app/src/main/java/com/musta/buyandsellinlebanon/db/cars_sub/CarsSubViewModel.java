package com.musta.buyandsellinlebanon.db.cars_sub;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.musta.buyandsellinlebanon.db.cars.Cars;
import com.musta.buyandsellinlebanon.db.cars.CarsRepository;

import java.util.ArrayList;
import java.util.List;

public class CarsSubViewModel extends AndroidViewModel {

    private CarsSubRepository mRepository;

    public CarsSubViewModel(Application application) {
        super(application);
        mRepository = new CarsSubRepository(application);
    }

    public void insertAll(ArrayList<CarsSub> carsSubs) {
        mRepository.insertAll(carsSubs);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<CarsSub>> getLiveDataCarsSub() {
        return mRepository.getLiveDataCarsSub();
    }

    public LiveData<List<CarsSub>> getLiveDataCarsSubByCarId(long carId) {
        return mRepository.getLiveDataCarsSubByCarId(carId);
    }

}