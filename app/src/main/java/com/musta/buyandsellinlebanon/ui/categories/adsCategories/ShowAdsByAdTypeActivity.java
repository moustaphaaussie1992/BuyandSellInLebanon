package com.musta.buyandsellinlebanon.ui.categories.adsCategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ui.home.ShowAllAdsRVAdapter;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShowAdsByAdTypeActivity extends AppCompatActivity {

    RecyclerView recyclerview;

    String adType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ads_by_ad_type);

        recyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(layoutManager);
//        recyclerview.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
//                DividerItemDecoration.VERTICAL));


        Intent intent = getIntent();
        if (intent.hasExtra("adType")) {
            adType = intent.getStringExtra("adType");
            String adTitle = intent.getStringExtra("adTitle");
            setTitle(adTitle);

            final ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Please wait...", true);

            // volley
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_SHOW_ALL_ADS);
            Map<String, String> params = new HashMap();
            params.put("adType", adType);
            GsonRequest<ShowAllAdsModel[]> myGsonRequest = new GsonRequest<ShowAllAdsModel[]>(Request.Method.POST, url, ShowAllAdsModel[].class, null, params,
                    new Response.Listener<ShowAllAdsModel[]>() {
                        @Override
                        public void onResponse(ShowAllAdsModel[] response) {
                            dialog.dismiss();
                            ShowAllAdsRVAdapter showAllAdsRVAdapter = new ShowAllAdsRVAdapter(Arrays.asList(response));
                            recyclerview.setAdapter(showAllAdsRVAdapter);
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

        } else {
            finish();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
