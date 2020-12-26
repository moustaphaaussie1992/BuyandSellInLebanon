package com.musta.buyandsellinlebanon.ui.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musta.buyandsellinlebanon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewAppFragment extends Fragment {

    public ReviewAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_app, container, false);
    }
}
