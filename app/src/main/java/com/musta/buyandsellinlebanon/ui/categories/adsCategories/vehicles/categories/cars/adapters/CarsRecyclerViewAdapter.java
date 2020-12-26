package com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.categories.cars.ShowCarsAdsActivity;
import com.musta.buyandsellinlebanon.db.cars.Cars;

import java.util.ArrayList;
import java.util.List;

public class CarsRecyclerViewAdapter extends RecyclerView.Adapter<CarsRecyclerViewAdapter.MyViewHolder> {

    List<Cars> cars, carsFiltered;
    Context context;
    String carsSaleOrRent;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
        }
    }

    public CarsRecyclerViewAdapter(List<Cars> cars, Context context, String carsSaleOrRent) {
        this.cars = cars;
        this.carsFiltered = cars;
        this.context = context;
        this.carsSaleOrRent = carsSaleOrRent;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cars_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Cars car = carsFiltered.get(position);

        if (context.getResources().getString(R.string.language).equals("en")) {
            holder.textView.setText(car.getCar_name());
        } else {
            holder.textView.setText(car.getCar_name_ar());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (car.getCar_id() == 0) {
//                    Intent intent = new Intent(context, ShowCarsAdsActivity.class);
//                    intent.putExtra("carsSaleOrRent", carsSaleOrRent);
//                    intent.putExtra("carId", "0");
//                    context.startActivity(intent);
//                } else {
                    Intent intent = new Intent(context, ShowCarsAdsActivity.class);
                    intent.putExtra("carsSaleOrRent", carsSaleOrRent);
                    intent.putExtra("carId", car.getCar_id()+"");
                    context.startActivity(intent);

//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carsFiltered.size();
    }

    public void filter(String searchText) {
        if (searchText.equals("")) {
            this.carsFiltered = this.cars;
            return;
        } else {
            this.carsFiltered = new ArrayList<Cars>();

            for (int i = 0; i < cars.size(); i++) {
                Cars item = cars.get(i);
                if (item.getCar_name().toLowerCase().contains(searchText) || item.getCar_name_ar().toLowerCase().contains(searchText)) {
                    this.carsFiltered.add(item);
                }
            }
        }
    }

}
