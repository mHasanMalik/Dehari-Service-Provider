package com.nfg.devlot.dehariprovider.Helpers;

import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstance extends FirebaseInstanceIdService {


    public static String token = "";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();


        token = FirebaseInstanceId.getInstance().getToken();

        Log.d("[*] FCM Access token", token);

    }

}
