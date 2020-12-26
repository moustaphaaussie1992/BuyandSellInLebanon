package com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.ShowAdsByAdTypeActivity;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars.CarsActivity;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.models.AdTypeTitleModel;
import com.musta.buyandsellinlebanon.utils.Constants;

import java.util.List;

public class AdTypeTitleRecyclerViewAdapter extends RecyclerView.Adapter<AdTypeTitleRecyclerViewAdapter.MyViewHolder> {

    List<AdTypeTitleModel> AdTypeTitleModels;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
        }
    }

    public AdTypeTitleRecyclerViewAdapter(List<AdTypeTitleModel> vehiclesModels, Context context) {
        this.AdTypeTitleModels = vehiclesModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicles_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AdTypeTitleModel AdTypeModel = AdTypeTitleModels.get(position);

        holder.textView.setText(AdTypeModel.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AdTypeModel.getValue().equals(Constants.CARS_FOR_SALE) ||
                        AdTypeModel.getValue().equals(Constants.CARS_FOR_RENT)) {
                    Intent intent = new Intent(context, CarsActivity.class);
                    intent.putExtra("carsSaleOrRent", AdTypeModel.getValue());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ShowAdsByAdTypeActivity.class);
                    intent.putExtra("adType", AdTypeModel.getValue());
                    intent.putExtra("adTitle", AdTypeModel.getText());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return AdTypeTitleModels.size();
    }


}
