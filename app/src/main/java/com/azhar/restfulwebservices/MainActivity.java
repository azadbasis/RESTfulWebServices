package com.azhar.restfulwebservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.restfulwebservices.model.DataItem;
import com.azhar.restfulwebservices.services.MyService;
import com.azhar.restfulwebservices.utils.NetworkHelper;
import com.azhar.restfulwebservices.utils.RequestPackage;

public class MainActivity extends AppCompatActivity {

    private TextView output;
    private boolean networkOk;
    public static final String JSON_URL = "http://560057.youcanlearnit.net/services/json/itemsfeed.php";
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //  String message = intent.getStringExtra(MyService.MY_SERVICE_PAYLOAD);
            DataItem[] dataItems = (DataItem[]) intent.getParcelableArrayExtra(MyService.MY_SERVICE_PAYLOAD);
            for (DataItem items : dataItems) {
                output.append(items.getItemName() + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver, new IntentFilter(MyService.MY_SERVICE_MESSAGE));
        networkOk = NetworkHelper.hasNetworkAccess(this);
        output.append("Network ok: " + networkOk);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }

    public void runClickHandler(View view) {
//        output.append("Button clicked\n");
        if (networkOk) {

            RequestPackage requestPackage = new RequestPackage();
            requestPackage.setEndPoint(JSON_URL);
            requestPackage.setParam("category", "Entrees");
            requestPackage.setMethod("GET");
            Intent intent = new Intent(this, MyService.class);
//            intent.setData(Uri.parse(JSON_URL));
            intent.putExtra(MyService.REQUEST_PACKAGE, requestPackage);
            startService(intent);

        } else {
            Toast.makeText(this, "Network is not available!", Toast.LENGTH_SHORT).show();
        }

    }

    public void clearClickHandler(View view) {
        output.setText("");
    }


}
