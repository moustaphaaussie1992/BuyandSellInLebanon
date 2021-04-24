package com.musta.buyandsellinlebanon.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    public static String SHARED_PREF_STRING = "MYSHAREDPREF";

    public static void signIn(Context context, String name, String username) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("username", username);
        editor.putString("isloggedin", "1");
        editor.commit();
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", "");
        editor.putString("username", "");
        editor.putString("isloggedin", "0");
        editor.commit();
    }


    public static boolean isLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);

//
//
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("username", "hadi");
//        editor.commit();
//        return true;


        String isLogged = prefs.getString("isloggedin", "0");
        if (isLogged.equals("1")) {
            return true;
        } else {
            return false;
        }






    }

    public static String getDisplayName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);
        return prefs.getString("name", "");
    }

    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);
        return prefs.getString("username", "");

    }

    public static String getUserToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);
        return prefs.getString("myusertoken", "");
    }

    public static void setUserToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREF_STRING, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("myusertoken", token);
        editor.commit();

    }

}
