package com.musta.buyandsellinlebanon.ui.categories.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ui.categories.dollar.DollarMarketActivity;
import com.musta.buyandsellinlebanon.ui.categories.models.CategoriesRVModel;
import com.musta.buyandsellinlebanon.utils.Constants;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.VehiclesActivity;

import java.util.List;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.MyViewHolder> {

    List<CategoriesRVModel> categoriesRVModelList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
            imageView = view.findViewById(R.id.imageView);
        }

    }

    public CategoriesRecyclerViewAdapter(List<CategoriesRVModel> categoriesRVModelList, Context context) {
        this.categoriesRVModelList = categoriesRVModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CategoriesRVModel categoriesRVModel = categoriesRVModelList.get(position);

        holder.textView.setText(categoriesRVModel.getText());
        switch (categoriesRVModel.getValue()) {
            case Constants.VEHICLES:
                holder.imageView.setBackgroundResource(R.drawable.car);
                break;
            case Constants.PROPERTIES:
                holder.imageView.setBackgroundResource(R.drawable.house);
                break;
            case Constants.MOBILES:
                holder.imageView.setBackgroundResource(R.drawable.smartphone);
                break;
            case Constants.ELECTRONICS_AND_HOME_APPLIANCES:
                holder.imageView.setBackgroundResource(R.drawable.television);
                break;
            case Constants.HOME_FURNITURE_AND_DECOR:
                holder.imageView.setBackgroundResource(R.drawable.beds);
                break;
            case Constants.FASHION_AND_BEAUTY:
                holder.imageView.setBackgroundResource(R.drawable.dress);
                break;
            case Constants.PETS:
                holder.imageView.setBackgroundResource(R.drawable.pawprint);
                break;
            case Constants.KIDS_AND_BABIES:
                holder.imageView.setBackgroundResource(R.drawable.children);
                break;
            case Constants.Sports_and_Equipments:
                holder.imageView.setBackgroundResource(R.drawable.fitness);
                break;
            case Constants.Hobbies_Music_Art_and_Books:
                holder.imageView.setBackgroundResource(R.drawable.paint);
                break;
            case Constants.Jobs:
                holder.imageView.setBackgroundResource(R.drawable.cleaner);
                break;
            case Constants.Business_and_Industrial:
                holder.imageView.setBackgroundResource(R.drawable.factory);
                break;
            case Constants.Services:
                holder.imageView.setBackgroundResource(R.drawable.support);
                break;
            case Constants.Planting_and_Food:
                holder.imageView.setBackgroundResource(R.drawable.food);
                break;
            case Constants.DOLLAR_MARKET:
                holder.imageView.setBackgroundResource(R.drawable.money);
                break;
            default:
                holder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.DOLLAR_MARKET.equals(categoriesRVModel.getValue())) {
                    Intent intent = new Intent(context, DollarMarketActivity.class);
                    context.startActivity(intent);

                } else {
                    Intent intent = new Intent(context, VehiclesActivity.class);
                    intent.putExtra("adTypeTitle", categoriesRVModel.getValue());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesRVModelList.size();
    }

}
