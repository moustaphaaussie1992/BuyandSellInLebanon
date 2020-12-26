package com.musta.buyandsellinlebanon.db.cars;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class CarsViewModel extends AndroidViewModel {

    private CarsRepository mRepository;

    public CarsViewModel(Application application) {
        super(application);
        mRepository = new CarsRepository(application);
    }

    public void insertAll(ArrayList<Cars> cars) {
        mRepository.insertAll(cars);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<Cars>> getLiveDataCars() {
        return mRepository.getLiveDataCars();
    }

}