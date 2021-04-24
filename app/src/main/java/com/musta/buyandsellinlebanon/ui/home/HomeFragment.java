package com.musta.buyandsellinlebanon.ui.home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private Button reviewAppButton;
   public RecyclerView recyclerviewHome;
    public SearchView searchposts;
    public Button searchbutton;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);




        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            // Catch event on [x] button inside search view
            int searchCloseButtonId = searchView.getContext().getResources()
                    .getIdentifier("android:id/search_close_btn", null, null);
            ImageView closeButton = (ImageView) this.searchView.findViewById(searchCloseButtonId);
// Set on click listener
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchView.setIconified(true);
                    recyclerviewHome = getView().findViewById(R.id.recyclerviewHome);
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
            });
        }

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                  return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {


                 //
                    //   Log.d("TAG", "afterTextChanged: "+"searchposts.getQuery().toString()");
                    final RecyclerView recyclerview = getView().findViewById(R.id.recyclerviewHome);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerview.setLayoutManager(layoutManager);
//        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));

                    final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                            "Please wait...", true);

                    // volley
                    String      url = NetworkHelper.getUrl(NetworkHelper.ACTION_ADS_BY_SEARCH);



                    Map<String, String> params = new HashMap();
                 //   Log.d("TAG", "afterTextChanged: "+searchposts.getQuery().toString());
                    params.put("text", query);
//        params.put("username", "moustaphaaussie@gmail.com");
                    GsonRequest<ShowAllAdsModel[]> myGsonRequest = new GsonRequest<ShowAllAdsModel[]>(Request.Method.POST, url, ShowAllAdsModel[].class, null, params,
                            new Response.Listener<ShowAllAdsModel[]>() {
                                @Override
                                public void onResponse(ShowAllAdsModel[] response) {
                                    dialog.dismiss();
                                    List<ShowAllAdsModel> list = new LinkedList<ShowAllAdsModel>(Arrays.asList(response));
                                    ShowAllAdsRVAdapter showAllAdsRVAdapter = new ShowAllAdsRVAdapter(list,getContext());
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

                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(myGsonRequest);


                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}