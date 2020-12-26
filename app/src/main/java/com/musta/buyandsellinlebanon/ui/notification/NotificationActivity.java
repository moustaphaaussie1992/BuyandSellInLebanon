package com.musta.buyandsellinlebanon.ui.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.musta.buyandsellinlebanon.MainActivity;
import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.models.NotificationAndCountModel;
import com.musta.buyandsellinlebanon.preferences.UserPreferences;
import com.musta.buyandsellinlebanon.utils.network.GsonRequest;
import com.musta.buyandsellinlebanon.utils.network.NetworkHelper;
import com.musta.buyandsellinlebanon.utils.network.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerView);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        if (UserPreferences.isLoggedIn(getApplicationContext())) {

            // volley
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_NOTIFICATIONS);
            Map<String, String> params = new HashMap();
            params.put("username", UserPreferences.getUsername(getApplicationContext()));
            GsonRequest<NotificationAndCountModel> myGsonRequest = new GsonRequest<NotificationAndCountModel>(Request.Method.POST, url, NotificationAndCountModel.class, null, params,
                    new Response.Listener<NotificationAndCountModel>() {
                        @Override
                        public void onResponse(NotificationAndCountModel response) {
//                            Gson gson = new Gson();
                            if (response.success) {
                                NotificationRVAdapter notificationRVAdapter = new NotificationRVAdapter(response.notifications);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                                        DividerItemDecoration.VERTICAL));
                                recyclerView.setAdapter(notificationRVAdapter);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkHelper.handleError(error);
                        }
                    });

            VolleySingleton.getInstance(NotificationActivity.this).addToRequestQueue(myGsonRequest);
        } else {
            finish();
        }

    }
}
