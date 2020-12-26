package com.musta.buyandsellinlebanon.ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.adapters.CategoriesCreateRecyclerViewAdapter;
import com.musta.buyandsellinlebanon.ui.categories.models.CategoriesRVModel;
import com.musta.buyandsellinlebanon.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class AdTypeTitleCreateActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_type_title_create);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        List<CategoriesRVModel> categoriesRVModels = Arrays.asList(
                new CategoriesRVModel(getString(R.string.vehicles), Constants.VEHICLES),
                new CategoriesRVModel(getString(R.string.properties), Constants.PROPERTIES),
                new CategoriesRVModel(getString(R.string.mobile_phones_and_accessories), Constants.MOBILES),
                new CategoriesRVModel(getString(R.string.electronics_and_home_appliances), Constants.ELECTRONICS_AND_HOME_APPLIANCES),
                new CategoriesRVModel(getString(R.string.home_furniture_and_decor), Constants.HOME_FURNITURE_AND_DECOR),
                new CategoriesRVModel(getString(R.string.fashion_and_beauty), Constants.FASHION_AND_BEAUTY),
                new CategoriesRVModel(getString(R.string.pets), Constants.PETS),
                new CategoriesRVModel(getString(R.string.kids_and_babies), Constants.KIDS_AND_BABIES),
                new CategoriesRVModel(getString(R.string.sports_and_equipments), Constants.Sports_and_Equipments),
                new CategoriesRVModel(getString(R.string.hobbies_music_art_and_books), Constants.Hobbies_Music_Art_and_Books),
                new CategoriesRVModel(getString(R.string.jobs), Constants.Jobs),
                new CategoriesRVModel(getString(R.string.business_and_industrial), Constants.Business_and_Industrial),
                new CategoriesRVModel(getString(R.string.services), Constants.Services),
                new CategoriesRVModel(getString(R.string.planting_and_food), Constants.Planting_and_Food),
                new CategoriesRVModel(getString(R.string.dollar_market), Constants.DOLLAR_MARKET)

        );
        CategoriesCreateRecyclerViewAdapter categoriesCreateRecyclerViewAdapter = new CategoriesCreateRecyclerViewAdapter(categoriesRVModels, getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(categoriesCreateRecyclerViewAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
