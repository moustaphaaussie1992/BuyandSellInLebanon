package com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ui.home.ShowAllAdsRVAdapter;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.utils.Constants;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShowCarsAdsActivity extends AppCompatActivity {

    RecyclerView showAllCarsRV;
    String carsSaleOrRent, is_sale;
    String carId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars_ads);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("carsSaleOrRent")) {
            carId = intent.getStringExtra("carId");
            carsSaleOrRent = intent.getStringExtra("carsSaleOrRent");
            if (carsSaleOrRent.equals(Constants.CARS_FOR_SALE)) {
                setTitle(getString(R.string.cars_for_sale));
                is_sale = "1";
            } else if (carsSaleOrRent.equals(Constants.CARS_FOR_RENT)) {
                setTitle(getString(R.string.cars_for_rent));
                is_sale = "0";
            } else {
                finish();
            }
        }


        showAllCarsRV = findViewById(R.id.showAllCarsRV);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        showAllCarsRV.setLayoutManager(layoutManager);
//        showAllCarsRV.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
//                DividerItemDecoration.VERTICAL));


        // volley
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_SHOW_ALL_ADS);
        Map<String, String> params = new HashMap();
        params.put("isSale", is_sale);
        params.put("carId", carId);
        params.put("adType", "car");
        GsonRequest<ShowAllAdsModel[]> myGsonRequest = new GsonRequest<ShowAllAdsModel[]>(Request.Method.POST, url, ShowAllAdsModel[].class, null, params,
                new Response.Listener<ShowAllAdsModel[]>() {
                    @Override
                    public void onResponse(ShowAllAdsModel[] response) {

                        ShowAllAdsRVAdapter showAllCarsRVAdapter = new ShowAllAdsRVAdapter(Arrays.asList(response));
                        showAllCarsRV.setAdapter(showAllCarsRVAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(ShowCarsAdsActivity.this).addToRequestQueue(myGsonRequest);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
