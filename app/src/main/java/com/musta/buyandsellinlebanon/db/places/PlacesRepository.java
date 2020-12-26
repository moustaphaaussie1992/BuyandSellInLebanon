package com.musta.buyandsellinlebanon.db.places;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.musta.buyandsellinlebanon.db.AppRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class PlacesRepository {

    private PlacesDao mDao;


    public PlacesRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mDao = db.placesDao();
    }

    void insertAll(final ArrayList<Places> palcesArrayList) {
        new AsyncTask<ArrayList<Places>, Void, Void>() {
            @Override
            protected Void doInBackground(final ArrayList<Places>... params) {
                for (Places place : params[0]) {
                    mDao.insert(place);
                }
                return null;
            }
        }.execute(palcesArrayList);
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

    LiveData<List<Places>> getLiveDataPlaces() {
        return mDao.getLiveDataPlaces();
    }


}
