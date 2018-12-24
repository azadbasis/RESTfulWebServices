package com.azhar.restfulwebservices;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyService extends IntentService {


    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE="MyServiceMessage";
    public static final String MY_SERVICE_PAYLOAD="MyServicePayload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Uri uri = intent.getData();
            Log.d(TAG, "onHandleIntent: " + uri.toString());

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Intent messageIntent=new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD,"Service all done!");
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
