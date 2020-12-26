package com.musta.buyandsellinlebanon.ads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.models.Comments;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;

import java.util.List;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.MyViewHolder> {

    List<Comments> comments;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewOutGoing, client_name, textviewIngoing,client_nameInGOING;

        public MyViewHolder(View view) {
            super(view);
            textviewIngoing = view.findViewById(R.id.textview);
            client_name = view.findViewById(R.id.client_name);
            textViewOutGoing = view.findViewById(R.id.textViewOutGoing);
            client_nameInGOING = view.findViewById(R.id.client_nameInGOING);
        }
    }


    public CommentsRecyclerViewAdapter(List<Comments> comments) {
        this.comments = comments;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_item_list, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Comments comment = comments.get(position);
        holder.textviewIngoing.setVisibility(View.GONE);
        holder.textViewOutGoing.setVisibility(View.GONE);
        holder.client_name.setVisibility(View.GONE);
        holder.client_nameInGOING.setVisibility(View.GONE);

        holder.textviewIngoing.setText(comment.getText());
        holder.textViewOutGoing.setText(comment.getText());

        holder.client_name.setText(comment.getName());
        holder.client_nameInGOING.setText(comment.getName());

        if (comment.getUsername().equals(UserPreferences.getUsername(context))) {
            holder.textViewOutGoing.setVisibility(View.VISIBLE);
            holder.client_name.setVisibility(View.VISIBLE);
        } else {
            holder.textviewIngoing.setVisibility(View.VISIBLE);
            holder.client_nameInGOING.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
