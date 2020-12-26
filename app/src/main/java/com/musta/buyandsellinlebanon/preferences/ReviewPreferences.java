package com.musta.buyandsellinlebanon.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ReviewPreferences {

    public static String SHARED_PREF_STRING = "MYSHAREDPREF";

    public static void reviewing(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("reviewed", "1");
        editor.commit();
    }

    public static boolean isReviewed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);
        String isReviewed = prefs.getString("reviewed", "0");
        if (isReviewed.equals("1")) {
            return true;
        } else {
            return false;
        }
    }
}
