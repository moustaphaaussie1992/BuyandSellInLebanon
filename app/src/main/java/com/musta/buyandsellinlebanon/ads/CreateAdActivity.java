package com.musta.buyandsellinlebanon.ads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.db.cars.Cars;
import com.musta.buyandsellinlebanon.db.cars.CarsViewModel;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSub;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSubViewModel;
import com.musta.buyandsellinlebanon.db.places.Places;
import com.musta.buyandsellinlebanon.db.places.PlacesViewModel;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.utils.Constants;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.toptoche.searchablespinnerlibrary.SearchableListDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CreateAdActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static String LANGUAGE = "en";

    private String radioCurrencyType = Constants.LIRA;

    public static final int PICK_IMAGES = 1;
    private List<Uri> userSelectedImageUriList = new ArrayList<>();
    private String adCreatetype = "";
    private String isSale = "";
    private String name = "";
    private String username = "";
    ProgressDialog dialog;


    private TextView how_much_images;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8;
    private TextInputEditText title_ad_form, description_ad_form,
            price_ad_form, distance_covered, car_year, phoneET;

    private SearchableSpinner spinnerCars, spinnerCarsModel, spinnerPlaces;
    private Spinner transmission_type;
    private String title, description, phone, distanceCovered, year, transmission_type_string, price;
    private long carSelectedId, carModelSelectedId, placesSelectedId;

    private LinearLayout spinnerCarsLayout, spinnerCarsModelLayout, transmission_type_layout, car_year_layout, distance_covered_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        if (UserPreferences.isLoggedIn(this)) {
            name = UserPreferences.getDisplayName(this);
            username = UserPreferences.getUsername(this);
        } else {
            Toast.makeText(getApplicationContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
            finish();
        }

        LANGUAGE = getString(R.string.language);

        title_ad_form = findViewById(R.id.title_ad_form);
        description_ad_form = findViewById(R.id.description_ad_form);
        price_ad_form = findViewById(R.id.price_ad_form);
        distance_covered = findViewById(R.id.distance_covered);
        car_year = findViewById(R.id.car_year);
        phoneET = findViewById(R.id.phone);
        spinnerPlaces = findViewById(R.id.spinnerPlaces);
        spinnerCars = findViewById(R.id.spinnerCars);
        spinnerCarsModel = findViewById(R.id.spinnerCarsModel);
        transmission_type = findViewById(R.id.transmission_type);


        spinnerCarsLayout = findViewById(R.id.spinnerCarsLayout);
        spinnerCarsModelLayout = findViewById(R.id.spinnerCarsModelLayout);
        transmission_type_layout = findViewById(R.id.transmission_type_layout);
        car_year_layout = findViewById(R.id.car_year_layout);
        distance_covered_layout = findViewById(R.id.distance_covered_layout);

        setTitle(R.string.create_new_ad);
        Intent intent = getIntent();
        if (intent.hasExtra("ad_create_type")) {
            adCreatetype = intent.getStringExtra("ad_create_type");
            String adCreatetypeTitle = intent.getStringExtra("ad_create_type_title");
            setTitle(getTitle() + " - " + adCreatetypeTitle);
            if (adCreatetype.equals(Constants.AD_SELL_CAR_TYPE)) {
                isSale = "1";
            } else if (adCreatetype.equals(Constants.AD_Rent_CAR_TYPE)) {
                isSale = "0";
            } else {
                spinnerCarsLayout.setVisibility(View.GONE);
                spinnerCarsModelLayout.setVisibility(View.GONE);
                transmission_type_layout.setVisibility(View.GONE);
                car_year_layout.setVisibility(View.GONE);
                distance_covered_layout.setVisibility(View.GONE);
            }
        } else {
            finish();
        }
        setSpinners();

        how_much_images = findViewById(R.id.how_much_images);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);
        image8.setOnClickListener(this);

//        ImageView testImageView = findViewById(R.id.testImageView);
//        Glide.with(this).
//                load("http://192.168.1.5/buy_and_sell_in_lebanon/web/imagesads/image1.jpeg").
//                into(testImageView);

    }

    public void addImages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_pictures)), PICK_IMAGES);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK && null != data) {


            if (userSelectedImageUriList == null) {
                userSelectedImageUriList = new ArrayList<Uri>();
            }
            Uri fileUri = data.getData();
            if (fileUri == null) {
                ClipData clip = data.getClipData();
                for (int i = 0; i < clip.getItemCount(); i++) {
                    ClipData.Item item = clip.getItemAt(i);
                    Uri uri = item.getUri();
                    if (userSelectedImageUriList.size() < 8) {
                        userSelectedImageUriList.add(uri);
                    }
                }
            } else {
                if (userSelectedImageUriList.size() < 8) {
                    userSelectedImageUriList.add(fileUri);
                }
            }


            try {
                for (int i = 0; i < userSelectedImageUriList.size(); i++) {
                    InputStream inputStream = getContentResolver().openInputStream(userSelectedImageUriList.get(i));
                }
                updateImages();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException ex) {
                Log.e("UPLOADINGPICTURES", ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...

        }
    }

    private void updateImages() {

        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        image3.setVisibility(View.GONE);
        image4.setVisibility(View.GONE);
        image5.setVisibility(View.GONE);
        image6.setVisibility(View.GONE);
        image7.setVisibility(View.GONE);
        image8.setVisibility(View.GONE);

        for (int i = 0; i < userSelectedImageUriList.size(); i++) {
            if (i == 0) {
                image1.setVisibility(View.VISIBLE);
                image1.setImageURI(null);
                image1.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 1) {
                image2.setVisibility(View.VISIBLE);
                image2.setImageURI(null);
                image2.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 2) {
                image3.setVisibility(View.VISIBLE);
                image3.setImageURI(null);
                image3.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 3) {
                image4.setVisibility(View.VISIBLE);
                image4.setImageURI(null);
                image4.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 4) {
                image5.setVisibility(View.VISIBLE);
                image5.setImageURI(null);
                image5.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 5) {
                image6.setVisibility(View.VISIBLE);
                image6.setImageURI(null);
                image6.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 6) {
                image7.setVisibility(View.VISIBLE);
                image7.setImageURI(null);
                image7.setImageURI(userSelectedImageUriList.get(i));
            } else if (i == 7) {
                image8.setVisibility(View.VISIBLE);
                image8.setImageURI(null);
                image8.setImageURI(userSelectedImageUriList.get(i));
            }
        }

        how_much_images.setText(userSelectedImageUriList.size() + "");
    }


    @Override
    public void onClick(View view) {
        if (view == image1) {
            imageViewDialog(0);
        } else if (view == image2) {
            imageViewDialog(1);
        } else if (view == image3) {
            imageViewDialog(2);
        } else if (view == image4) {
            imageViewDialog(3);
        } else if (view == image5) {
            imageViewDialog(4);
        } else if (view == image6) {
            imageViewDialog(5);
        } else if (view == image7) {
            imageViewDialog(6);
        } else if (view == image8) {
            imageViewDialog(7);

        }
    }

    private void imageViewDialog(final int imageIndex) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.what_would_you_like_to_do));

        String[] options = {
                getString(R.string.remove_image),
                getString(R.string.cancel)
        };

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // remove image
                        userSelectedImageUriList.remove(imageIndex);
                        updateImages();
                        break;

                    case 4: // Cancel
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createAd() {
        title = title_ad_form.getText().toString();
        description = description_ad_form.getText().toString();
        distanceCovered = distance_covered.getText().toString();
        year = car_year.getText().toString();
        phone = phoneET.getText().toString();
        price = price_ad_form.getText().toString();


        if (isSale.equals("")) {
            if (!title.equals("") && !description.equals("") && !phone.equals("")
                    && !price.equals("")) {
                createAdNadUploadImages();
            } else {
                Toast.makeText(this, R.string.please_fill_required_fields, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!title.equals("") && !description.equals("") && !phone.equals("")
                    && !price.equals("") && !year.equals("") && !distanceCovered.equals("")) {
                createAdNadUploadImages();
            } else {
                Toast.makeText(this, R.string.please_fill_required_fields, Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void createAdNadUploadImages() {
        try {
            dialog = ProgressDialog.show(CreateAdActivity.this, "",
                    "Please wait...", true);
            List<String> images = new ArrayList<>();
            for (int i = 0; i < userSelectedImageUriList.size(); i++) {
                final InputStream imageStream = getContentResolver().
                        openInputStream(userSelectedImageUriList.get(i));
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //encoding image to string
                String image = getStringImage(selectedImage);
                if (!image.equals("")) {
                    images.add(image);
                }
            }
            SendImage(images);

        } catch (IOException e) {
            dialog.dismiss();
            e.printStackTrace();
        }
    }

    public String getStringImage(Bitmap bmp) {

        int currSize;
        int currQuality = 100;
        int maxSizeBytes = 800000;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, currQuality, baos);


        do {
            if (currQuality < 15) {
                return "";
            }
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, currQuality, baos);
            currSize = baos.toByteArray().length;
            currQuality = currQuality - 5;

        } while (currSize >= maxSizeBytes);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void SendImage(final List<String> images) {
//        final ProgressDialog dialog = ProgressDialog.show(CreateAdActivity.this, "",
//                "Please wait...", true);

        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_CREATE_AD);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        finish();
                        Log.d("upload", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(CreateAdActivity.this, "No internet connection", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                for (int i = 0; i < images.size(); i++) {
                    params.put("image" + (i + 1), images.get(i));
                }
                params.put("imagesSize", images.size() + "");
//                params.put("adType", adCreatetype);
                String adType = "";
                if (isSale == "1") {
                    adType = Constants.AD_SELL_CAR_TYPE;
                } else if (isSale == "0") {
                    adType = Constants.AD_Rent_CAR_TYPE;
                } else {
                    adType = adCreatetype;
                }
                params.put("adType", adType);

                if (adType.equals(Constants.AD_SELL_CAR_TYPE) || adType.equals(Constants.AD_Rent_CAR_TYPE)) {
                    params.put("isSale", isSale);
                    params.put("distanceCovered", distanceCovered);
                    params.put("year", year);
                    params.put("carId", carSelectedId + "");
                    params.put("carSubId", carModelSelectedId + "");
                    params.put("transmissionType", transmission_type_string);
                } else {

                }
                params.put("name", name);
                params.put("username", username);
                params.put("title", title);
                params.put("description", description);
                params.put("phone", phone);
                params.put("price", price);
                params.put("placeid", placesSelectedId + "");
                params.put("priceUnit", radioCurrencyType);
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
    }

    private void setSpinners() {

        CarsViewModel carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
        carsViewModel.getLiveDataCars().observe(this, new Observer<List<Cars>>() {
            @Override
            public void onChanged(@Nullable List<Cars> cars) {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(CreateAdActivity.this,
                        android.R.layout.simple_spinner_item, cars);

                // Specify layout to be used when list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                spinnerCars.setAdapter(adapter);
                spinnerCars.setOnItemSelectedListener(CreateAdActivity.this);
            }

        });
        PlacesViewModel placesViewModel;
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);

        placesViewModel.getLiveDataPlaces().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(@Nullable List<Places> places) {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(CreateAdActivity.this,
                        android.R.layout.simple_spinner_item, places);
                // Specify layout to be used when list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                spinnerPlaces.setAdapter(adapter);
                spinnerPlaces.setOnItemSelectedListener(CreateAdActivity.this);
            }

        });


        List<String> transmissionTypes = new ArrayList<>();

        transmissionTypes.add(getString(R.string.automatic));
        transmissionTypes.add(getString(R.string.manual));
        transmissionTypes.add(getString(R.string.steptronic));


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(CreateAdActivity.this,
                android.R.layout.simple_spinner_item, transmissionTypes);

        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        transmission_type.setAdapter(adapter);
        transmission_type.setOnItemSelectedListener(CreateAdActivity.this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.spinnerCars) {
            final Cars car = (Cars) spinnerCars.getSelectedItem();
            CarsSubViewModel carsSubViewModel = ViewModelProviders.of(this).get(CarsSubViewModel.class);
            carsSubViewModel.getLiveDataCarsSubByCarId(car.getCar_id()).observe(this, new Observer<List<CarsSub>>() {
                @Override
                public void onChanged(List<CarsSub> carsSubs) {
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(CreateAdActivity.this,
                            android.R.layout.simple_spinner_item, carsSubs);

                    carSelectedId = car.getCar_id();
                    // Specify layout to be used when list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Applying the adapter to our spinner
                    spinnerCarsModel.setAdapter(adapter);
                    spinnerCarsModel.setOnItemSelectedListener(CreateAdActivity.this);
                }
            });


        } else if (adapterView.getId() == R.id.spinnerCarsModel) {
            final CarsSub carsSub = (CarsSub) spinnerCarsModel.getSelectedItem();
            carModelSelectedId = carsSub.getModel_id();
        } else if (adapterView.getId() == R.id.transmission_type) {
            transmission_type_string = transmission_type.getSelectedItem().toString();
        } else if (adapterView.getId() == R.id.spinnerPlaces) {
            final Places places = (Places) spinnerPlaces.getSelectedItem();
            placesSelectedId = places.getPlace_id();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                createAd();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_lira:
                if (checked)
                    radioCurrencyType = Constants.LIRA;
                break;
            case R.id.radio_dollar:
                if (checked)
                    radioCurrencyType = Constants.DOLLAR;
                break;
        }
    }
}
