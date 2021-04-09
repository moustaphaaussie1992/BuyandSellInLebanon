package com.musta.buyandsellinlebanon.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.BuildConfig;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.AdTypeTitleCreateActivity;
import com.musta.buyandsellinlebanon.ads.BeforeCreateAdActivity;
import com.musta.buyandsellinlebanon.ads.CreateAdActivity;
import com.musta.buyandsellinlebanon.preferences.ReviewPreferences;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.ui.signin.LoginActivity;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private Button reviewAppButton;
    RecyclerView recyclerviewHome;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reviewAppButton = view.findViewById(R.id.reviewAppButton);
        if (!ReviewPreferences.isReviewed(getContext())) {
            reviewAppButton.setVisibility(View.VISIBLE);
            reviewAppButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                    }
                    ReviewPreferences.reviewing(getContext());
                    reviewAppButton.setVisibility(View.GONE);
                }
            });
        }
//        create_new_ad = view.findViewById(R.id.create_new_ad);
//        create_new_ad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (UserPreferences.isLoggedIn(getContext())) {
//                    startActivity(new Intent(getActivity(), AdTypeTitleCreateActivity.class));
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        recyclerviewHome = view.findViewById(R.id.recyclerviewHome);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerviewHome.setLayoutManager(layoutManager);
//        recyclerviewHome.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Please wait...", true);

        // volley
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_SHOW_ALL_ADS);
        Map<String, String> params = new HashMap();
        params.put("adType", "all");
        GsonRequest<ShowAllAdsModel[]> myGsonRequest = new GsonRequest<ShowAllAdsModel[]>(Request.Method.POST, url, ShowAllAdsModel[].class, null, params,
                new Response.Listener<ShowAllAdsModel[]>() {
                    @Override
                    public void onResponse(ShowAllAdsModel[] response) {
                        dialog.dismiss();
                        ShowAllAdsRVAdapter showAllAdsRVAdapter = new ShowAllAdsRVAdapter(Arrays.asList(response),getContext());
                        recyclerviewHome.setAdapter(showAllAdsRVAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(myGsonRequest);

    }
}