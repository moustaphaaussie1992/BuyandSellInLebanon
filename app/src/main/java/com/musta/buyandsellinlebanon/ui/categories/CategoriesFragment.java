package com.musta.buyandsellinlebanon.ui.categories;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.BeforeCreateAdActivity;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.ui.categories.adapters.CategoriesRecyclerViewAdapter;
import com.musta.buyandsellinlebanon.ui.categories.models.CategoriesRVModel;
import com.musta.buyandsellinlebanon.utils.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    RecyclerView homeRecyclerView;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeRecyclerView = view.findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(layoutManager);
        List<CategoriesRVModel> categoriesRVModels = Arrays.asList(
                new CategoriesRVModel(getString(R.string.vehicles), Constants.VEHICLES),
                new CategoriesRVModel(getString(R.string.properties), Constants.PROPERTIES),
                new CategoriesRVModel(getString(R.string.mobile_phones_and_accessories), Constants.MOBILES),
                new CategoriesRVModel(getString(R.string.home_furniture_and_decor), Constants.HOME_FURNITURE_AND_DECOR),
                new CategoriesRVModel(getString(R.string.fashion_and_beauty), Constants.FASHION_AND_BEAUTY),
                new CategoriesRVModel(getString(R.string.pets), Constants.PETS),
                new CategoriesRVModel(getString(R.string.kids_and_babies), Constants.KIDS_AND_BABIES),
                new CategoriesRVModel(getString(R.string.sports_and_equipments), Constants.Sports_and_Equipments),
                new CategoriesRVModel(getString(R.string.hobbies_music_art_and_books), Constants.Hobbies_Music_Art_and_Books),
                new CategoriesRVModel(getString(R.string.jobs), Constants.Jobs),
                new CategoriesRVModel(getString(R.string.business_and_industrial), Constants.Business_and_Industrial),
                new CategoriesRVModel(getString(R.string.services), Constants.Services),
                new CategoriesRVModel(getString(R.string.planting_and_food), Constants.Planting_and_Food)
               // new CategoriesRVModel(getString(R.string.dollar_market), Constants.DOLLAR_MARKET)
        );
        CategoriesRecyclerViewAdapter categoriesRecyclerViewAdapter = new CategoriesRecyclerViewAdapter(categoriesRVModels, getContext());
        homeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        homeRecyclerView.setAdapter(categoriesRecyclerViewAdapter);


    }

}
