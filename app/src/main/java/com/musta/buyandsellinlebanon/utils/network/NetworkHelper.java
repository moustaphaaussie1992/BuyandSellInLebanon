package com.musta.buyandsellinlebanon.utils.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class NetworkHelper {

    public static final String TAG = NetworkHelper.class.getName();

    //    public static final String SERVER_IP = "dollartolebanesecurrency.com";
//    public static final String SERVER_IP = "localhost/buy_and_sell_in_lebanon";
//    public static final String SERVER_IP = "192.168.1.4/buy_and_sell_in_lebanon";
//    public static final String SERVER_IP = "192.168.43.113/buy_and_sell_in_lebanon";
//    public static final String SERVER_IP = "192.168.43.78/buy_and_sell_in_lebanon";
//    public static final String SERVER_IP = "192.168.43.78/buy_and_sell_in_lebanon";
    public static final String SERVER_IP = "5.189.150.68/buy_and_sell_in_lebanon";


    public static final String IMAGES_PATH = "http://" + SERVER_IP + "/web/imagesads/";
    public static final String IMAGES_PATH_DOLLAR = "http://" + SERVER_IP + "/web/dollartobeviedinshare.jpg";

    //    public static final String ACTION_GET_NOTIFICATION_AND_COUNT_OF_READ = "mobile/notifications-and-count-of-unread";
    public static final String ACTION_GET_COUNT_OF_READ = "mobile/count-of-unread";
    public static final String ACTION_GET_NOTIFICATIONS = "mobile/notifications";
    public static final String ACTION_GET_ALL_DATA = "mobile/get-all-data";
    public static final String ACTION_CREATE_AD = "mobile/upload-test";
    public static final String ACTION_GET_SHOW_CARS_ADS = "mobile/show-cars-ads";
    public static final String ACTION_REGISTER_CLIENT = "mobile/register-client";
    public static final String ACTION_GET_SHOW_ALL_ADS = "mobile/show-all-ads";
    public static final String ACTION_GET_ONE_AD = "mobile/get-one-ad";
    public static final String ACTION_GET_AD_DETAIL = "mobile/get-ad-detail-by-ad-id";
    public static final String ACTION_GET_AD = "mobile/get-ad-by-ad-id";
    public static final String ACTION_ADS_BY_USER = "mobile/ads-by-user";
    public static final String ACTION_REMOVE_AD = "mobile/remove-ad-by-id";
    public static final String ACTION_AD_COMMENT = "mobile/create-ad-comment";
    public static final String ACTION_GET_AD_COMMENTS = "mobile/get-ad-comments";

    public static final String ACTION_CREATE_DOLLAR_MARKET = "dollar/create";
    public static final String ACTION_GET_DOLLAR_MARKET = "dollar/get-data";

    public static String getUrl(String action) {
        String serverURL = "http://" + SERVER_IP + "/web/index.php?r=";
        String url = serverURL + "api/" + action;
        return url;
    }

    public static void handleError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        String statusCode = "", message = "";
        //statCode
        if (response != null && response.data != null) {
            statusCode = ": " + Integer.toString(error.networkResponse.statusCode);
        }
        //message
        try {
            if (error instanceof NetworkError) {
                message = "networkError";
            } else if (error instanceof ClientError) {
                message = "clientError";
            } else if (error instanceof ServerError) {
                message = "serverError";
            } else if (error instanceof AuthFailureError) {
                message = "authFailureError";
            } else if (error instanceof ParseError) {
                message = "parseError";
            } else if (error instanceof NoConnectionError) {
                message = "noConnectionError";
            } else if (error instanceof TimeoutError) {
                message = "timeoutError";
            } else {
                message = "defaultError";
            }
            Log.d(TAG, "Network message error: " + message + " " + statusCode);

        } catch (Exception e) {

        }
    }


}
