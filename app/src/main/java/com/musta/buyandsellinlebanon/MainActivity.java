package com.musta.buyandsellinlebanon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.musta.buyandsellinlebanon.ads.AdDetailActivity;
import com.musta.buyandsellinlebanon.ads.AdTypeTitleCreateActivity;
import com.musta.buyandsellinlebanon.ads.BeforeCreateAdActivity;
import com.musta.buyandsellinlebanon.ads.models.AdDetailModel;
import com.musta.buyandsellinlebanon.db.cars.CarsViewModel;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSub;
import com.musta.buyandsellinlebanon.db.cars_sub.CarsSubViewModel;
import com.musta.buyandsellinlebanon.db.places.PlacesViewModel;
import com.musta.buyandsellinlebanon.db.pojo.Data;
import com.musta.buyandsellinlebanon.db.pojo.Error;
import com.musta.buyandsellinlebanon.db.pojo.ServerResponse;
import com.musta.buyandsellinlebanon.models.AdModel;
import com.musta.buyandsellinlebanon.models.NotificationAndCountModel;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.ui.categories.dollar.DollarMarketActivity;
import com.musta.buyandsellinlebanon.ui.home.ShowAllAdsRVAdapter;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.ui.notification.NotificationActivity;
import com.musta.buyandsellinlebanon.ui.signin.LoginActivity;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    GoogleSignInClient mGoogleSignInClient;

    TextView display_name_header, email_header, drawer_header_sign_in;

    PlacesViewModel placesViewModel;
    CarsViewModel carsViewModel;
    CarsSubViewModel carsSubViewModel;
    private AdView mAdView;
    //    private InterstitialAd mInterstitialAd;
    FloatingActionButton create_new_ad;
    int countUnread; 

    private boolean shouldLoadAds;
    TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && getIntent().hasExtra("adId")) {
            String adId = bundle.getString("adId");
//            Toast.makeText(this, adId, Toast.LENGTH_SHORT).show();

            // volley
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_ONE_AD);
            Map<String, String> params = new HashMap();
            params.put("adId", adId);
            GsonRequest<ShowAllAdsModel> myGsonRequest = new GsonRequest<ShowAllAdsModel>(Request.Method.POST, url, ShowAllAdsModel.class, null, params,
                    new Response.Listener<ShowAllAdsModel>() {
                        @Override
                        public void onResponse(ShowAllAdsModel response) {

                            Intent intent = new Intent(getApplicationContext(), AdDetailActivity.class);
                            intent.putExtra("id", response.getId());
                            intent.putExtra("title", response.getTitle());
                            intent.putExtra("date", response.getCreation_date());
                            intent.putExtra("placeName", response.getPlace_name());
                            intent.putExtra("description", response.getDescription());
                            intent.putExtra("phone", response.getPhone());
                            String priceUnitStr = "";
                            if (response.getPrice_unit().equals("dollar")) {
                                priceUnitStr = getString(R.string.dollar);
                            } else {
                                priceUnitStr = getString(R.string.lira);
                            }
                            intent.putExtra("price", response.getPrice() + priceUnitStr);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkHelper.handleError(error);
                        }
                    });

            VolleySingleton.getInstance(this).addToRequestQueue(myGsonRequest);


        }

        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().subscribeToTopic("main")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "msg subscribed";
                        if (!task.isSuccessful()) {
                            msg = "msg subscribe failed";
                        }
                    }
                });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("clientfbrid", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        UserPreferences.setUserToken(getApplicationContext(), token);
//                        Log.d("clientfbridtoken", token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.interstitialAd));
////        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_categories,
                R.id.nav_home,
                R.id.nav_my_ads,
//                R.id.nav_dollar_market,
//                R.id.nav_collect_and_win,
//                R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_tools,
//                R.id.nav_send,
                R.id.nav_share,
                R.id.nav_review
//                R.id.nav_sign_in
        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        display_name_header = navigationView.getHeaderView(0).findViewById(R.id.display_name_header);
        email_header = navigationView.getHeaderView(0).findViewById(R.id.email_header);
        drawer_header_sign_in = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_sign_in);

//        PlacesViewModel placesViewModel;
//        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
//
//        placesViewModel.getLiveDataPlaces().observe(this, new Observer<List<Places>>() {
//            @Override
//            public void onChanged(@Nullable List<Places> places) {
//                int size = places.size();
//            }
//
//        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        getAllData();
        try {
            setUpFirebaseDynamicLinks();
        } catch (Exception e) {

        }
        //for testing
//        startActivity(new Intent(this, VehiclesActivity.class));

        create_new_ad = findViewById(R.id.create_new_ad);
        create_new_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserPreferences.isLoggedIn(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), AdTypeTitleCreateActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Toast.makeText(getApplicationContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_noti);
        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
//        textCartItemCount.setText("");
        return true;
    }

    public void setupBadge() {
        if (UserPreferences.isLoggedIn(getApplicationContext())) {
            // volley
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_COUNT_OF_READ);
            Map<String, String> params = new HashMap();
            params.put("username", UserPreferences.getUsername(getApplicationContext()));
            GsonRequest<NotificationAndCountModel> myGsonRequest = new GsonRequest<NotificationAndCountModel>(Request.Method.POST, url, NotificationAndCountModel.class, null, params,
                    new Response.Listener<NotificationAndCountModel>() {
                        @Override
                        public void onResponse(NotificationAndCountModel response) {
//                            Gson gson = new Gson();
                            if (response.success) {
                                countUnread = response.unread_count;
                                if (countUnread == 0) {
                                    textCartItemCount.setVisibility(View.INVISIBLE);
                                } else {
                                    textCartItemCount.setVisibility(View.VISIBLE);
                                    if (countUnread > 99) {
                                        textCartItemCount.setText("99");
                                    } else {
                                        textCartItemCount.setText(countUnread + "");
                                    }
                                }
//                                Data data = gson.fromJson(response.data, Data.class);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkHelper.handleError(error);
                        }
                    });

            VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(myGsonRequest);


        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_noti:
                if (UserPreferences.isLoggedIn(getApplicationContext())) {
//                    if (countUnread ==0) {
                        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                        startActivity(intent);
                    textCartItemCount.setVisibility(View.GONE);
//                    }else{
//                        Toast.makeText(this, R.string.you_have_no_notification, Toast.LENGTH_SHORT).show();
//                    }
                } else {

                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        invalidateOptionsMenu();
//        if (mInterstitialAd.isLoaded() && shouldLoadAds) {
//            mInterstitialAd.show();
//            mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
        updateDrawerHeader();
    }

    @Override
    public void onStart() {
        super.onStart();
        shouldLoadAds = true;
    }

    @Override
    public void onStop() {
        shouldLoadAds = false;
        super.onStop();
    }

    private void updateDrawerHeader() {
        if (UserPreferences.isLoggedIn(getApplicationContext())) {
            display_name_header.setVisibility(View.VISIBLE);
            email_header.setVisibility(View.VISIBLE);
            display_name_header.setText(UserPreferences.getDisplayName(getApplicationContext()));
            email_header.setText(UserPreferences.getUsername(getApplicationContext()));
            drawer_header_sign_in.setText(R.string.logout);
            drawer_header_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            UserPreferences.logout(getApplicationContext());
                            invalidateOptionsMenu();
                            updateDrawerHeader();
                        }
                    });
                }
            });
        } else {
            display_name_header.setVisibility(View.GONE);
            email_header.setVisibility(View.GONE);
            drawer_header_sign_in.setText(R.string.sign_in);
            drawer_header_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void getAllData() {

        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        carsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
        carsSubViewModel = ViewModelProviders.of(this).get(CarsSubViewModel.class);

        // volley
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_ALL_DATA);
        Map<String, String> params = new HashMap();
        GsonRequest<ServerResponse> myGsonRequest = new GsonRequest<ServerResponse>(Request.Method.POST, url, ServerResponse.class, null, params,
                new Response.Listener<ServerResponse>() {
                    @Override
                    public void onResponse(ServerResponse response) {
                        Gson gson = new Gson();
                        if (response.success) {
                            Data data = gson.fromJson(response.data, Data.class);
                            delete(data);
                            insert(data);
//                            refreshData();
                        } else {
                            Error error = gson.fromJson(response.data.toString(), Error.class);
                            if (error != null) {
                                Toast toasty = Toast.makeText(MainActivity.this, error.message, Toast.LENGTH_LONG);
                                toasty.setGravity(Gravity.CENTER, 0, 0);
                                toasty.show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(myGsonRequest);

    }

    private void delete(Data data) {
        if (data.places != null) {
            placesViewModel.deleteAll();
        }
        if (data.cars != null) {
            carsViewModel.deleteAll();
        }
        if (data.cars_sub != null) {
            carsSubViewModel.deleteAll();
        }
    }

    public void insert(final Data data) {
        if (data.places != null) {
            placesViewModel.insertAll(data.places);
        }
        if (data.cars != null) {
            carsViewModel.insertAll(data.cars);
        }
        if (data.cars_sub != null) {
            carsSubViewModel.insertAll(data.cars_sub);
        }
    }

    public void shareApp(MenuItem item) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_the_app));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
    }

    public void reviewApp(MenuItem view) {
        // TODO Auto-generated method stub
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
        }
    }

//    public void goToDollarMarket(MenuItem view) {
//        startActivity(new Intent(MainActivity.this, DollarMarketActivity.class));
//    }

    public void createNewAd(MenuItem item) {
        if (UserPreferences.isLoggedIn(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), AdTypeTitleCreateActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            Toast.makeText(getApplicationContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
        }
    }

    public void setUpFirebaseDynamicLinks() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        Log.e("share", "success ");
                        if (pendingDynamicLinkData != null) {
                            String link = pendingDynamicLinkData.getLink().toString();
                            String[] _ids = link.split("/\\?");
                            String[] data = _ids[_ids.length - 1].split("=");
                            if (data.length < 2) {
                                return;
                            }
                            final String id = data[1];
                            if (data[0].equals("ad")) {

                                // volley
                                String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_AD);
                                Map<String, String> params = new HashMap();
                                params.put("id", id);
                                GsonRequest<AdModel> myGsonRequest = new GsonRequest<AdModel>(Request.Method.POST, url, AdModel.class, null, params,
                                        new Response.Listener<AdModel>() {
                                            @Override
                                            public void onResponse(AdModel response) {
                                                if (response != null) {
                                                    Intent intent = new Intent(getApplicationContext(), AdDetailActivity.class);
                                                    intent.putExtra("id", id);
                                                    intent.putExtra("title", response.getTitle());
                                                    intent.putExtra("date", response.getCreation_date());
                                                    if (getString(R.string.language).equals("en")) {
                                                        intent.putExtra("placeName", response.getPlace_name_en());
                                                    } else {
                                                        intent.putExtra("placeName", response.getPlace_name_ar());
                                                    }

                                                    intent.putExtra("description", response.getDescription());
                                                    intent.putExtra("phone", response.getPhone());
                                                    String priceUnitStr = "";
                                                    if (response.getPrice_unit().equals("dollar")) {
                                                        priceUnitStr = getString(R.string.dollar);
                                                    } else {
                                                        priceUnitStr = getString(R.string.lira);
                                                    }
                                                    intent.putExtra("price", response.getPrice() + priceUnitStr);
                                                    startActivity(intent);

                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                NetworkHelper.handleError(error);
                                            }
                                        });

                                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(myGsonRequest);


                            } else if (data[0].equals("dollar")) {
                                startActivity(new Intent(getApplicationContext(), DollarMarketActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "no ad", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("share", "link " + link);
                        } else {
                            Log.e("share", "Null");
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("share", "error");
                    }
                });
    }


}
