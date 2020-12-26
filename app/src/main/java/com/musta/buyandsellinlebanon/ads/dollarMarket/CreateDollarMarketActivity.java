package com.musta.buyandsellinlebanon.ads.dollarMarket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.db.places.Places;
import com.musta.buyandsellinlebanon.db.places.PlacesViewModel;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CreateDollarMarketActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner dollar_market_type_spinner, spinnerPlaces;
    private long placesSelectedId;
    private String dollar_market_type;
    TextInputEditText amount, price, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dollar_market);

        amount = findViewById(R.id.amount);
        price = findViewById(R.id.price);
        phone = findViewById(R.id.phone);

        dollar_market_type_spinner = findViewById(R.id.dollar_market_type_spinner);
        spinnerPlaces = findViewById(R.id.spinnerPlaces);

        setSpinners();
    }

    private void setSpinners() {


        PlacesViewModel placesViewModel;
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);

        placesViewModel.getLiveDataPlaces().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(@Nullable List<Places> places) {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(CreateDollarMarketActivity.this,
                        android.R.layout.simple_spinner_item, places);
                // Specify layout to be used when list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                spinnerPlaces.setAdapter(adapter);
                spinnerPlaces.setOnItemSelectedListener(CreateDollarMarketActivity.this);
            }

        });


        List<String> transmissionTypes = new ArrayList<>();

        transmissionTypes.add(getString(R.string.buy));
        transmissionTypes.add(getString(R.string.sell));


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(CreateDollarMarketActivity.this,
                android.R.layout.simple_spinner_item, transmissionTypes);

        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        dollar_market_type_spinner.setAdapter(adapter);
        dollar_market_type_spinner.setOnItemSelectedListener(CreateDollarMarketActivity.this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerPlaces) {
            final Places places = (Places) spinnerPlaces.getSelectedItem();
            placesSelectedId = places.getPlace_id();
        } else if (parent.getId() == R.id.dollar_market_type_spinner) {
            if (dollar_market_type_spinner.getSelectedItem().toString().equals(getString(R.string.buy))) {
                dollar_market_type = "buy";
            } else {
                dollar_market_type = "sell";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void create() {

        if (!amount.getText().toString().equals("")
                && !price.getText().toString().equals("")
                && !phone.getText().toString().equals("")
                && !dollar_market_type.equals("")
        ) {
            final ProgressDialog dialog = ProgressDialog.show(CreateDollarMarketActivity.this, "",
                    "Please wait...", true);

            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_CREATE_DOLLAR_MARKET);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(CreateDollarMarketActivity.this, "No internet connection", Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new Hashtable<String, String>();

                    params.put("amount", amount.getText().toString() + "");
                    params.put("price", price.getText().toString() + "");
                    params.put("phone", phone.getText().toString() + "");
                    params.put("place", placesSelectedId + "");
                    params.put("type", dollar_market_type);

                    return params;
                }
            };
            {
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        } else {
            Toast.makeText(this, R.string.please_fill_required_fields, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_ad_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_ad_from_menu:
                create();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
