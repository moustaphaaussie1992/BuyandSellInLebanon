package com.musta.buyandsellinlebanon.ui.myads.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.AdDetailActivity;
import com.musta.buyandsellinlebanon.ui.home.ShowAllAdsRVAdapter;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.utils.FirebaseUtils;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdsRVAdapter extends RecyclerView.Adapter<MyAdsRVAdapter.MyViewHolder> {

    List<ShowAllAdsModel> showAllAdsModels;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ad_image;
        public TextView textViewTitle;
        public TextView textViewPrice;
        public TextView textViewCreetionDate;
        public TextView textViewPlace;
        public Button shareAdButton;

        public MyViewHolder(View view) {
            super(view);
            ad_image = view.findViewById(R.id.ad_image);
            textViewTitle = view.findViewById(R.id.title);
            textViewPrice = view.findViewById(R.id.price);
            textViewCreetionDate = view.findViewById(R.id.creation_date);
            textViewPlace = view.findViewById(R.id.place_name);
            shareAdButton = view.findViewById(R.id.shareAdButton);
        }
    }

    public MyAdsRVAdapter(List<ShowAllAdsModel> showAllAdsModels) {
        this.showAllAdsModels = showAllAdsModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_ads_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ShowAllAdsModel showAd = showAllAdsModels.get(position);

        final String imagePath = NetworkHelper.IMAGES_PATH + showAd.getImage();
        Glide.with(context)
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ad_image);

        holder.textViewTitle.setText(showAd.getTitle());
        String priceUnitStr = "";
        if (showAd.getPrice_unit().equals("dollar")) {
            priceUnitStr = context.getString(R.string.dollar);
        } else {
            priceUnitStr = context.getString(R.string.lira);
        }
        holder.textViewPrice.setText(showAd.getPrice() + priceUnitStr);
        holder.textViewCreetionDate.setText(showAd.getCreation_date());
        holder.textViewPlace.setText(showAd.getPlace_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.what_would_you_like_to_do));

                String[] options = {
                        context.getString(R.string.view),
                        context.getString(R.string.remove),
                        context.getString(R.string.cancel)
                };

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // remove image
                                Intent intent = new Intent(context, AdDetailActivity.class);
                                intent.putExtra("id", showAd.getId());
                                intent.putExtra("title", showAd.getTitle());
                                intent.putExtra("date", showAd.getCreation_date());
                                intent.putExtra("placeName", showAd.getPlace_name());
                                intent.putExtra("description", showAd.getDescription());
                                intent.putExtra("phone", showAd.getPhone());
                                String priceUnitStr = "";
                                if (showAd.getPrice_unit().equals("dollar")) {
                                    priceUnitStr = context.getString(R.string.dollar);
                                } else {
                                    priceUnitStr = context.getString(R.string.lira);
                                }
                                intent.putExtra("price", showAd.getPrice() + priceUnitStr);
                                context.startActivity(intent);
                                break;
                            case 1: // remove image


                                final ProgressDialog dialog1 = ProgressDialog.show(context, "",
                                        context.getString(R.string.please_wait), true);
                                // volley
                                String url = NetworkHelper.getUrl(NetworkHelper.ACTION_REMOVE_AD);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if (response.contains("success")) {
                                                    showAllAdsModels.remove(position);
                                                    notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                                                }
                                                dialog1.dismiss();

                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                dialog1.dismiss();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("adId", showAd.getId());
                                        return params;
                                    }
                                };

                                VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

                                break;

                            case 2: // Cancel
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        holder.shareAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/?ad=" + showAd.getId());
                FirebaseUtils.buildDeepLinkVideo(context, uri, imagePath, showAd.getTitle(), showAd.getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return showAllAdsModels.size();
    }


}
