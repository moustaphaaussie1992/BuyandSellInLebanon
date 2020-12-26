package com.musta.buyandsellinlebanon.ads.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.models.AdDetailPicturesModel;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;

public class PictureItemFragment extends Fragment {

    AdDetailPicturesModel picture;
    public ImageView imageView;
    public View view;


    public PictureItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picture_item, container, false);
        imageView = view.findViewById(R.id.imageView);


        String url = NetworkHelper.IMAGES_PATH + picture.getPicture_name();
        Glide.with(getContext()).load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(imageView);

        return view;
    }

    public void setData(AdDetailPicturesModel picture) {
        this.picture = picture;
    }

}


