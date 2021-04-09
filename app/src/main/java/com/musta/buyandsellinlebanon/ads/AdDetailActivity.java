package com.musta.buyandsellinlebanon.ads;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.adapters.CommentsRecyclerViewAdapter;
import com.musta.buyandsellinlebanon.ads.adapters.ViewPagerAdapter;
import com.musta.buyandsellinlebanon.ads.fragments.PictureItemFragment;
import com.musta.buyandsellinlebanon.ads.models.AdDetailCarModel;
import com.musta.buyandsellinlebanon.ads.models.AdDetailModel;
import com.musta.buyandsellinlebanon.ads.models.AdDetailPicturesModel;
import com.musta.buyandsellinlebanon.ads.models.Comments;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.utils.FirebaseUtils;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class AdDetailActivity extends AppCompatActivity {

    String adId = "";
    ViewPager pictures_viewpager;
    ViewPagerAdapter adapter;
    PageIndicatorView pageIndicatorView;
    LinearLayout detailsLayout;
    TextView description;
    String phoneStr;
    Button shareAdButton, btnSend;
    EditText textInput;
    RecyclerView comments_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);


        description = findViewById(R.id.description);
        pictures_viewpager = findViewById(R.id.pictures_viewpager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        detailsLayout = findViewById(R.id.detailsLayout);
        shareAdButton = findViewById(R.id.shareAdButton);
        textInput = findViewById(R.id.textInput);
        btnSend = findViewById(R.id.btnSend);

        comments_recyclerview = findViewById(R.id.comments_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        comments_recyclerview.setLayoutManager(layoutManager);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            adId = intent.getExtras().getString("id");
            final String title = intent.getExtras().getString("title");
            String date = intent.getExtras().getString("date");
            String placeName = intent.getExtras().getString("placeName");
            String price = intent.getExtras().getString("price");
            final String descriptionStr = intent.getExtras().getString("description");
            phoneStr = intent.getExtras().getString("phone");

            addDetailListItem(getString(R.string.title), title);
            addDetailListItem(getString(R.string.price), price);
            addDetailListItem(getString(R.string.date), date);
            addDetailListItem(getString(R.string.place), placeName);
            addDetailListItem(getString(R.string.phone), phoneStr);
            description.setText(descriptionStr);

            // volley
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_AD_DETAIL);
            Map<String, String> params = new HashMap();
            params.put("id", adId);
            GsonRequest<AdDetailModel> myGsonRequest = new GsonRequest<AdDetailModel>(Request.Method.POST, url, AdDetailModel.class, null, params,
                    new Response.Listener<AdDetailModel>() {
                        @Override
                        public void onResponse(final AdDetailModel response) {
//                            Log.d("myresponse", response.toString());
                            setupViewPager(response.pictures);
                            if (response.adDetail != null) {
                                setupDetailsCar(response.adDetail);
                            }

                            shareAdButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Uri uri = Uri.parse("https://play.google.com/?ad=" + adId);
                                        FirebaseUtils.buildDeepLinkVideo(AdDetailActivity.this, uri, NetworkHelper.IMAGES_PATH + response.pictures.get(0).getPicture_name(), title, descriptionStr);

                                    } catch (Exception e) {

                                    }
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkHelper.handleError(error);
                        }
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(myGsonRequest);

            setupComments();
        }
    }


    public void setupDetailsCar(AdDetailCarModel adDetailCarModel) {

//        addDetailListItem(getString(R.string.price,));
        if (getString(R.string.language).equals("en")) {
            addDetailListItem(getString(R.string.car_type), adDetailCarModel.getCar());
        } else {
            addDetailListItem(getString(R.string.car_type), adDetailCarModel.getCar_ar());
        }
        if (getString(R.string.language).equals("en")) {
            addDetailListItem(getString(R.string.car_model), adDetailCarModel.getCar_sub());
        } else {
            addDetailListItem(getString(R.string.car_model), adDetailCarModel.getCar_sub_ar());
        }
        addDetailListItem(getString(R.string.year), adDetailCarModel.getYear());
//        addDetailListItem("title 1", "value 1");
//        addDetailListItem("title 1", "value 123");

    }


    public void addDetailListItem(String text, String value) {

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 0, 8);
        relativeLayout.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        TextView textViewTitle = new TextView(this);
        textViewTitle.setText(text);
        textViewTitle.setTextColor(getResources().getColor(R.color.black));
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_START);
        textViewTitle.setLayoutParams(layoutParams1);
        TextView textViewValue = new TextView(this);
        textViewValue.setText(value);
        textViewValue.setTextColor(getResources().getColor(R.color.black));
        textViewValue.setTypeface(textViewValue.getTypeface(), Typeface.BOLD);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_END);
        textViewValue.setLayoutParams(layoutParams2);
        relativeLayout.addView(textViewTitle);
        relativeLayout.addView(textViewValue);
        detailsLayout.addView(relativeLayout);

    }

    public void setupViewPager(List<AdDetailPicturesModel> picturesList) {
        adapter = new ViewPagerAdapter((((FragmentActivity) this).getSupportFragmentManager()));
        for (int i = 0; i < picturesList.size(); i++) {
            AdDetailPicturesModel picture = picturesList.get(i);
            PictureItemFragment pictureItemFragment = new PictureItemFragment();
            pictureItemFragment.setData(picture);
            adapter.addFragment(pictureItemFragment, "");
        }
        pageIndicatorView.setAnimationType(AnimationType.THIN_WORM);
        pageIndicatorView.setCount(picturesList.size()); // specify
        pictures_viewpager.setAdapter(adapter);
        pictures_viewpager.setCurrentItem(0);
        pictures_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
        pageIndicatorView.setViewPager(pictures_viewpager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void call(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneStr, null));
        startActivity(intent);
    }

    public void whatsapp(View view) {
        String contact = "+961 " + phoneStr; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(AdDetailActivity.this, R.string.whatsapp_is_not_installed_in_your_phone, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void sendComment(View view) {
        final String text = textInput.getText().toString();
        if (!text.equals("")) {
            btnSend.setEnabled(false);
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_AD_COMMENT);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                textInput.setText("");
                                setupComments();
                            } else {
                                Toast.makeText(AdDetailActivity.this, "حدث خطأ", Toast.LENGTH_LONG).show();
                            }
                            btnSend.setEnabled(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AdDetailActivity.this, "حدث خطأ", Toast.LENGTH_LONG).show();
                            btnSend.setEnabled(true);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("text", text);
                    params.put("ad", adId);
                    params.put("clientUsername", UserPreferences.getUsername(getApplicationContext()));
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
    }

    private void setupComments() {
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_AD_COMMENTS);
        Map<String, String> params = new HashMap();
        params.put("ad", adId);
        GsonRequest<Comments[]> myGsonRequest = new GsonRequest<Comments[]>(Request.Method.POST, url, Comments[].class, null, params,
                new Response.Listener<Comments[]>() {
                    @Override
                    public void onResponse(Comments[] response) {
                        comments_recyclerview.setAdapter(new CommentsRecyclerViewAdapter(Arrays.asList(response)));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(AdDetailActivity.this).addToRequestQueue(myGsonRequest);
    }
}
