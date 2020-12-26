package com.musta.buyandsellinlebanon.db.places;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class PlacesViewModel extends AndroidViewModel {

    private PlacesRepository mRepository;

    public PlacesViewModel(Application application) {
        super(application);
        mRepository = new PlacesRepository(application);
    }

    public void insertAll(ArrayList<Places> places) {
        mRepository.insertAll(places);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<Places>> getLiveDataPlaces() {
        return mRepository.getLiveDataPlaces();
    }

}
