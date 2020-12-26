package com.musta.buyandsellinlebanon.db.cars;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.musta.buyandsellinlebanon.db.AppRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class CarsRepository {

    private CarsDao mDao;

    public CarsRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mDao = db.carsDao();
    }


    void insertAll(final ArrayList<Cars> carsArrayList) {
        new AsyncTask<ArrayList<Cars>, Void, Void>() {
            @Override
            protected Void doInBackground(final ArrayList<Cars>... params) {
                for (Cars car : params[0]) {
                    mDao.insert(car);
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

    LiveData<List<Cars>> getLiveDataCars() {
        return mDao.getLiveDataCars();
    }

}
