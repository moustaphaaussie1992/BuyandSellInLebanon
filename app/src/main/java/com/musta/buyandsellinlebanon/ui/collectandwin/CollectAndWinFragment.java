package com.musta.buyandsellinlebanon.ui.collectandwin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectAndWinFragment extends Fragment {

    public CollectAndWinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collect_and_win, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserPreferences.isLoggedIn(getContext())) {

        } else {

        }
    }
}
