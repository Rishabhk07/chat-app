package com.example.hptouchsmart.chatapp;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by hp TouchSmart on 7/28/2016.
 */
public class MsgService extends FirebaseMessagingService {

    public MsgService() {
        Log.d("TAG" ,"msg service cons called !! ");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG" , "msg received called !! ");
        Log.d("TAG" , "From:" + remoteMessage.getFrom());
        Log.d("TAG" , "Message payload : " +remoteMessage.getData().toString() );

        Map<String , String > msgData = remoteMessage.getData();
        for(String s : msgData.keySet()){
            Log.d("TAG" , "Message : "+ msgData.get(s) );
        }

    }
}
