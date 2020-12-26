package com.musta.buyandsellinlebanon.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.AdDetailActivity;
import com.musta.buyandsellinlebanon.models.NotificationModel;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.MyViewHolder> {

    List<NotificationModel> notificationModels;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
        }

    }

    public NotificationRVAdapter(List<NotificationModel> categoriesRVModelList) {
        this.notificationModels = categoriesRVModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NotificationModel notificationModel = notificationModels.get(position);

        String action = " ";
        if (notificationModel.getType().equals("comment")) {
            action = context.getString(R.string.commented);
            action = action + " ";
            action = action + context.getString(R.string.on_your_ad);
        }


        holder.textView.setText(notificationModel.getFrom_client() + " " + action + " ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // volley
                String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_ONE_AD);
                Map<String, String> params = new HashMap();
                params.put("adId", notificationModel.getAd_id());
                GsonRequest<ShowAllAdsModel> myGsonRequest = new GsonRequest<ShowAllAdsModel>(Request.Method.POST, url, ShowAllAdsModel.class, null, params,
                        new Response.Listener<ShowAllAdsModel>() {
                            @Override
                            public void onResponse(ShowAllAdsModel response) {

                                Intent intent = new Intent(context, AdDetailActivity.class);
                                intent.putExtra("id", response.getId());
                                intent.putExtra("title", response.getTitle());
                                intent.putExtra("date", response.getCreation_date());
                                intent.putExtra("placeName", response.getPlace_name());
                                intent.putExtra("description", response.getDescription());
                                intent.putExtra("phone", response.getPhone());
                                String priceUnitStr = "";
                                if (response.getPrice_unit().equals("dollar")) {
                                    priceUnitStr = context.getString(R.string.dollar);
                                } else {
                                    priceUnitStr = context.getString(R.string.lira);
                                }
                                intent.putExtra("price", response.getPrice() + priceUnitStr);
                                context.startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                NetworkHelper.handleError(error);
                            }
                        });

                VolleySingleton.getInstance(context).addToRequestQueue(myGsonRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

}
