package com.musta.buyandsellinlebanon.ui.myads;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.ui.home.ShowAllAdsRVAdapter;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.ui.myads.adapters.MyAdsRVAdapter;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsFragment extends Fragment {

    RecyclerView recyclerview;

    public MyAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ads, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerview_my_ads);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
//        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Please wait...", true);

        // volley
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_ADS_BY_USER);
        Map<String, String> params = new HashMap();
        params.put("username", UserPreferences.getUsername(getContext()));
//        params.put("username", "moustaphaaussie@gmail.com");
        GsonRequest<ShowAllAdsModel[]> myGsonRequest = new GsonRequest<ShowAllAdsModel[]>(Request.Method.POST, url, ShowAllAdsModel[].class, null, params,
                new Response.Listener<ShowAllAdsModel[]>() {
                    @Override
                    public void onResponse(ShowAllAdsModel[] response) {
                        dialog.dismiss();
                        List<ShowAllAdsModel> list = new LinkedList<ShowAllAdsModel>(Arrays.asList(response));
                        MyAdsRVAdapter myAdsRVAdapter = new MyAdsRVAdapter(list);
                        recyclerview.setAdapter(myAdsRVAdapter);
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
