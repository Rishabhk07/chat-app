package com.example.hptouchsmart.chatapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class InstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "TAG";

    public InstanceIdService() {

        Log.d(TAG, "Service created !! ");
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG , "token generated:" +token);

    }
}