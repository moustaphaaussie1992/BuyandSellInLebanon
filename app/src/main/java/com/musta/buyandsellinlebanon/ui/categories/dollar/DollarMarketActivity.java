package com.musta.buyandsellinlebanon.ui.categories.dollar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.dollarMarket.CreateDollarMarketActivity;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DollarMarketActivity extends AppCompatActivity {

    RecyclerView dollar_market_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dollar_market);

        dollar_market_rv = findViewById(R.id.dollar_market_rv);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dollar_market_rv.setLayoutManager(layoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please wait...", true);

        // volley
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_DOLLAR_MARKET);
        GsonRequest<DollarMarketModel[]> myGsonRequest = new GsonRequest<DollarMarketModel[]>(Request.Method.POST, url, DollarMarketModel[].class, null, null,
                new Response.Listener<DollarMarketModel[]>() {
                    @Override
                    public void onResponse(DollarMarketModel[] response) {
                        dialog.dismiss();
                        DollarMarketRVAdapter dollarMarketRVAdapter = new DollarMarketRVAdapter(Arrays.asList(response));
                        dollar_market_rv.setAdapter(dollarMarketRVAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(myGsonRequest);
//        DollarMarketRVAdapter dollarMarketRVAdapter = new DollarMarketRVAdapter()
    }

    public void add(View view) {
        startActivity(new Intent(getApplicationContext(), CreateDollarMarketActivity.class));
    }
}
