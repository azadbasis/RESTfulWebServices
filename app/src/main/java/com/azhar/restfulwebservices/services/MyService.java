package com.azhar.restfulwebservices.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.azhar.restfulwebservices.model.DataItem;
import com.azhar.restfulwebservices.utils.HttpHelper;
import com.google.gson.Gson;

import java.io.IOException;

public class MyService extends IntentService {


    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE="MyServiceMessage";
    public static final String MY_SERVICE_PAYLOAD="MyServicePayload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            Uri uri = intent.getData();
            Log.d(TAG, "onHandleIntent: " + uri.toString());

        String response;
        try {
            response = HttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson=new Gson();
        DataItem[] dataItems=gson.fromJson(response,DataItem[].class);

        Intent messageIntent=new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD,dataItems);
        LocalBroadcastManager manager=LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
