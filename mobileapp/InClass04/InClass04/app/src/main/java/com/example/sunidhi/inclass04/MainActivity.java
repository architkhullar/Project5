package com.example.sunidhi.inclass04;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
    APIInterface apiService;
    ImageView imageView;

    ArrayList<Results.ResultsValue> productlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        apiService = APIClient.getClient().create(APIInterface.class);
        listView = findViewById( R.id.discountListView );

        final ProductRequest productRequest = new ProductRequest();
        productRequest.setRegion("lifestyle");
        Call<ArrayList<Results.ResultsValue>> call = apiService.getProducts(productRequest);
        call.enqueue(new Callback<ArrayList<Results.ResultsValue>>() {
            @Override
            public void onResponse(Call<ArrayList<Results.ResultsValue>> call, Response<ArrayList<Results.ResultsValue>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    productlist = response.body();

                    getImage();

                }else{
                    System.out.println("null in return");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<Results.ResultsValue>> call, Throwable t) {
                System.out.println("failed" + t.getLocalizedMessage());
            }
        });











//         imageView = findViewById(R.id.imageView2);
//
//        String input = "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";
//
//        byte[] decodedString = Base64.decode(input, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        imageView.setImageBitmap(decodedByte);


       /* String json_string = null;
        try{
            InputStream inputStream = getAssets().open( "discount.json" );
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json_string = new String( buffer, "UTF-8" );
            Gson gson = new Gson();
            Results results = gson.fromJson( json_string, Results.class );
            resultsValueList = results.getResults();
            Log.d( "demo", "resultsValueList = " + resultsValueList );


            discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, resultsValueList );
            listView.setAdapter( adapter );*/
//
//            beaconManager = new BeaconManager(this);
//            region = new BeaconRegion("ranged region",
//                    UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
//
//            beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
//                @Override
//                public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
//                    if (!list.isEmpty()) {
//                        Beacon nearestBeacon = list.get(0);
//                        List<String> places = placesNearBeacon(nearestBeacon);
//                        // TODO: update the UI here
//                        Log.d("Airport", "Nearest places: " + places);
//
//
//                    }
//                }
//            });
//
//
//
        /*} catch (IOException e) {
            Toast.makeText( this, "inside catch", Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        }
*/

    }

    private void getImage() {
/*

        System.out.println(productlist.toString());
        for(final Results.ResultsValue rv: productlist)
        {
            ImageRequest imageRequest = new ImageRequest();
            imageRequest.setphoto(rv.getPhoto());
            Call<ResponseBody> call1  =apiService.getImage(imageRequest);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        try {
                            System.out.println(response.body().string());
                            rv.setDataurl(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("null in return");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Failed in getting image");
                }
            });



        }
        System.out.println(productlist.toString());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 20000);
*/


        discountAdapter adapter = new discountAdapter( MainActivity.this, R.layout.product_info, productlist );
        System.out.println("time up"  +productlist.toString());
        listView.setAdapter(adapter);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        SystemRequirementsChecker.checkWithDefaultDialogs(this);
//
//        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
//            @Override
//            public void onServiceReady() {
//                beaconManager.startRanging(region);
//            }
//        });
//    }
//
//    @Override
//    protected void onPause() {
//        beaconManager.stopRanging(region);
//
//        super.onPause();
//    }
//
//    private static final Map<String, List<String>> PLACES_BY_BEACONS;
//    static {
//        Map<String, List<String>> placesByBeacons = new HashMap<>();
//        placesByBeacons.put("55125:738", new ArrayList<String>() {{
//            add("Grocery");
////            regionName = "grocery";
//        }});
//        placesByBeacons.put("59599:33091", new ArrayList<String>() {{
//            add("Lifestyle");
////            regionName = "lifestyle";
//        }});
//        placesByBeacons.put("1564:34409", new ArrayList<String>() {{
//            add("Produce");
////            regionName = "produce";
//        }});
//        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
//    }
//
//    private List<String> placesNearBeacon(Beacon beacon) {
//        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
//        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
//            Toast.makeText( this, "" + PLACES_BY_BEACONS.get( beaconKey), Toast.LENGTH_SHORT ).show();
////            Log.d("demo", "beaconKey = " + beaconKey);
////            regionName = String.valueOf( PLACES_BY_BEACONS.get(0) );
////            String a = beacon.getUniqueKey();
////            Log.d( "demo", "a = " + a );
////            Log.d( "demo", "regionName= " + regionName );
////            fun(regionName, resultsValueList);
////            regionName = beaconKey;
////            region = PLACES_BY_BEACONS.get( beaconKey);
//            return PLACES_BY_BEACONS.get(beaconKey);
//        }
//        return Collections.emptyList();
//    }
//
////    public static void fun(String regionName, List<Results.ResultsValue> resultsValueList){
////
////        for (int i = 0; i < resultsValueList.size(); i++) {
////            if(resultsValueList.get( i ).getRegion() == regionName){
////                newResultsValueList.add( resultsValueList.get( i ) );
////            }
////        }
////
////        Log.d( "demo", "newResultsValueList = " + newResultsValueList );
////    }
}
