package com.example.sunidhi.inclass04;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private BeaconRegion region;
    static ListView listView;
    static List<Results.ResultsValue> resultsValueList =  null;
    static List<Results.ResultsValue> newResultsValueList =  null;
    static String regionName="";
    static APIInterface apiService;
    ImageView imageView;
    Boolean connected  =false;
    static discountAdapter adapter;
    static ArrayList<Results.ResultsValue> productlist = new ArrayList<>();
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = APIClient.getClient().create(APIInterface.class);
        listView = findViewById(R.id.discountListView);
        context=MainActivity.this;

        final ProductRequest productRequest = new ProductRequest();
        productRequest.setRegion("grocery");
        Call<ArrayList<Results.ResultsValue>> call = apiService.getAllProducts();
        call.enqueue(new Callback<ArrayList<Results.ResultsValue>>() {
            @Override
            public void onResponse(Call<ArrayList<Results.ResultsValue>> call, Response<ArrayList<Results.ResultsValue>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    productlist = response.body();

                    discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, productlist );
                    System.out.println("first time loading"  +productlist.toString());
                    listView.setAdapter(adapter);

                }else{
                    System.out.println("null in return");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<Results.ResultsValue>> call, Throwable t) {
                System.out.println("failed" + t.getLocalizedMessage());
            }
        });



        adapter = new discountAdapter(this, R.layout.product_info, MyApplication.productlist);

        requestPermission(MainActivity.this);
        //setAdapter(productRequest);

        adapter = new discountAdapter(MainActivity.this, R.layout.product_info, MyApplication.productlist);


        listView.setAdapter(adapter);
        beaconManager = new BeaconManager(MainActivity.this);
    }

    static void updatelistview(){
        adapter.notifyDataSetChanged();
        System.out.println(MyApplication.productlist.size());
        Adapter disadapter = new discountAdapter(context, R.layout.product_info, MyApplication.productlist);
        listView.setAdapter((ListAdapter) disadapter);
        System.out.println("in update list view");
    }


    private void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 101);
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    boolean isGpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    boolean isNetworkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    //Location permission is given. Check if the providers are available and start location updates.
                    if (isGpsProviderEnabled && isNetworkProviderEnabled) {
                        connected = true;
                    } else {
                       }
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    boolean should = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (should) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},101);
                    } else {
                    }
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

   }
