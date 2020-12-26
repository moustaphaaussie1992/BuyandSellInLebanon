package com.musta.buyandsellinlebanon.ui.categories.dollar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.AdDetailActivity;
import com.musta.buyandsellinlebanon.utils.FirebaseUtils;

import java.util.List;

import static com.musta.buyandsellinlebanon.utils.network.NetworkHelper.IMAGES_PATH_DOLLAR;

public class DollarMarketRVAdapter extends RecyclerView.Adapter<DollarMarketRVAdapter.MyViewHolder> {

    List<DollarMarketModel> dollarMarketList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type, amount, price, place, date;
        public AppCompatImageView call, whatsapp;
        public Button shareDollarPrice;

        public MyViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.type);
            amount = view.findViewById(R.id.amount);
            price = view.findViewById(R.id.price);
            place = view.findViewById(R.id.place);
            date = view.findViewById(R.id.date);
            call = view.findViewById(R.id.call);
            whatsapp = view.findViewById(R.id.whatsapp);
            shareDollarPrice = view.findViewById(R.id.shareDollarPrice);
        }
    }

    public DollarMarketRVAdapter(List<DollarMarketModel> dollarMarketList) {
        this.dollarMarketList = dollarMarketList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dollar_market_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DollarMarketModel dollarMarket = dollarMarketList.get(position);
        if (context.getString(R.string.language).equals("en")) {
            holder.place.setText(dollarMarket.getPlace_en());
        } else {
            holder.place.setText(dollarMarket.getPlace_ar());
        }
        holder.type.setText(dollarMarket.getType());
        holder.amount.setText(dollarMarket.getAmount());
        holder.price.setText(dollarMarket.getPrice());
        holder.date.setText(dollarMarket.getCreation_date());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dollarMarket.getPhone(), null));
                context.startActivity(intent);
            }
        });

        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+961 " + dollarMarket.getPhone(); // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = context.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(context, R.string.whatsapp_is_not_installed_in_your_phone, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        holder.shareDollarPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/?dollar=1");
                String title = "";
                if(dollarMarket.getType().equals("buy")){
                    title = title + "مطلوب";
                }else{
                    title = title + "موجود";
                }
                title = title + " " + dollarMarket.getAmount() + "على"+ " " + dollarMarket.getPrice() + " " + "أدخل لرؤية المزيد من العروضات";
                FirebaseUtils.buildDeepLinkVideo(context, uri, IMAGES_PATH_DOLLAR, title, "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return dollarMarketList.size();
    }


}