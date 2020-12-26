package com.musta.buyandsellinlebanon.db.cars_sub;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.musta.buyandsellinlebanon.db.AppRoomDatabase;
import com.musta.buyandsellinlebanon.db.cars.Cars;
import com.musta.buyandsellinlebanon.db.cars.CarsDao;

import java.util.ArrayList;
import java.util.List;

public class CarsSubRepository {

    private CarsSubDao mDao;

    public CarsSubRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mDao = db.carsSubDao();
    }


    void insertAll(final ArrayList<CarsSub> carsArrayList) {
        new AsyncTask<ArrayList<CarsSub>, Void, Void>() {
            @Override
            protected Void doInBackground(final ArrayList<CarsSub>... params) {
                for (CarsSub carsSub : params[0]) {
                    mDao.insert(carsSub);
                }
                return null;
            }
        }.execute(carsArrayList);
    }

    void deleteAll() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                mDao.deleteAll();
                return null;
            }
        }.execute();
    }

    LiveData<List<CarsSub>> getLiveDataCarsSub() {
        return mDao.getLiveDataCarsSub();
    }

    LiveData<List<CarsSub>> getLiveDataCarsSubByCarId(long carId) {
        return mDao.getLiveDataCarsSubByCarId(carId);
    }

}
