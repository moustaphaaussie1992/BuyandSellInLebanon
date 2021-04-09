package com.musta.buyandsellinlebanon.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.AdDetailActivity;
import com.musta.buyandsellinlebanon.ui.home.models.ShowAllAdsModel;
import com.musta.buyandsellinlebanon.utils.FirebaseUtils;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;

import java.util.List;

import static com.musta.buyandsellinlebanon.utils.Constants.PICTURE_EXTENSION;

public class ShowAllAdsRVAdapter extends RecyclerView.Adapter<ShowAllAdsRVAdapter.MyViewHolder> {

    List<ShowAllAdsModel> showAllAdsModels;
    Context context;
    Context myContext;
    private InterstitialAd mInterstitialAd;


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
//            shareAdButton = view.findViewById(R.id.shareAdButton);

        }
    }

    public ShowAllAdsRVAdapter(List<ShowAllAdsModel> showAllAdsModels,Context myContext) {
        this.showAllAdsModels = showAllAdsModels;


        mInterstitialAd = new InterstitialAd(myContext);
        mInterstitialAd.setAdUnitId(myContext.getString(R.string.interstitialAd));
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
        holder.textViewCreetionDate.setText(showAd.getCreation_date().substring(0, 10));
        holder.textViewPlace.setText(showAd.getPlace_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                } else {
                    Log.d("dsdsddd", "The interstitial wasn't loaded yet.");
                }

            }
        });
//        holder.shareAdButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri uri = Uri.parse("https://play.google.com/?ad=" + showAd.getId());
//                FirebaseUtils.buildDeepLinkVideo(context, uri, imagePath, showAd.getTitle(), showAd.getDescription());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return showAllAdsModels.size();
    }


}
