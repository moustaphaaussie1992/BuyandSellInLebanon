package com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars.adapters.CarsRecyclerViewAdapter;
import com.musta.buyandsellinlebanon.db.cars.Cars;
import com.musta.buyandsellinlebanon.db.cars.CarsViewModel;
import com.musta.buyandsellinlebanon.utils.Constants;

import java.util.List;

public class CarsActivity extends AppCompatActivity {

    RecyclerView carsRecyclerView;
    CarsRecyclerViewAdapter carsRecyclerViewAdapter;
    EditText searchItems;
    String carsSaleOrRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("carsSaleOrRent")) {
            carsSaleOrRent = intent.getStringExtra("carsSaleOrRent");
            if (carsSaleOrRent.equals(Constants.CARS_FOR_SALE)) {
                setTitle(getString(R.string.cars_for_sale));
            } else if (carsSaleOrRent.equals(Constants.CARS_FOR_RENT)) {
                setTitle(getString(R.string.cars_for_rent));
            } else {
                finish();
            }
        }


        searchItems = findViewById(R.id.searchItems);
        carsRecyclerView = findViewById(R.id.carsRecyclerView);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        carsRecyclerView.setLayoutManager(layoutManager);
        carsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));

        CarsViewModel placesViewModel;
        placesViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);

        placesViewModel.getLiveDataCars().observe(this, new Observer<List<Cars>>() {
            @Override
            public void onChanged(@Nullable List<Cars> cars) {

                Cars carsShowAllItem = new Cars();
                carsShowAllItem.setCar_id(0);
                carsShowAllItem.setCar_name(getString(R.string.show_all));
                carsShowAllItem.setCar_name_ar(getString(R.string.show_all));
                cars.add(0, carsShowAllItem);

                carsRecyclerViewAdapter = new CarsRecyclerViewAdapter(cars, CarsActivity.this, carsSaleOrRent);

                carsRecyclerView.setAdapter(carsRecyclerViewAdapter);

                searchItems.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        Toast.makeText(GetItemsPriceActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                        carsRecyclerViewAdapter.filter(s.toString());
                        carsRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
