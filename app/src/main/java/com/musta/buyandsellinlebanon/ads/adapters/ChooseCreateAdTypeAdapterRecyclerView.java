package com.musta.buyandsellinlebanon.ads.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.CreateAdActivity;
import com.musta.buyandsellinlebanon.ads.models.CreateAdTypeModel;

import java.util.List;

public class ChooseCreateAdTypeAdapterRecyclerView extends RecyclerView.Adapter<ChooseCreateAdTypeAdapterRecyclerView.MyViewHolder> {

    List<CreateAdTypeModel> createAdType;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
        }
    }

    public ChooseCreateAdTypeAdapterRecyclerView(List<CreateAdTypeModel> createAdType, Context context) {
        this.createAdType = createAdType;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_car_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CreateAdTypeModel createAdTypeModel = createAdType.get(position);

        holder.textView.setText(createAdTypeModel.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateAdActivity.class);
                intent.putExtra("ad_create_type", createAdTypeModel.getValue());
                intent.putExtra("ad_create_type_title", createAdTypeModel.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return createAdType.size();
    }


}
