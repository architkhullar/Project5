package com.example.sunidhi.inclass04;

        import android.app.Application;
        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
        import com.estimote.coresdk.recognition.packets.Beacon;
        import com.estimote.coresdk.recognition.utils.EstimoteBeacons;
        import com.estimote.coresdk.scanning.bluetooth.settings.EstimoteScanParams;
        import com.estimote.coresdk.service.BeaconManager;
        import com.estimote.mgmtsdk.connection.protocol.characteristic.EstimoteService;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.UUID;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


/**
 * Created by Sunidhi on 27-Sep-18.
 */

public class MyApplication extends Application {
    static String regionname = "";
    private BeaconManager beaconManager;
    static ListView listView;
    static List<Results.ResultsValue> resultsValueList = null;
    static List<Results.ResultsValue> newResultsValueList = null;
    static String regionName = "";
    static APIInterface apiService;
    ImageView imageView;
    Boolean connected = false;
    static discountAdapter adapter;
    static ArrayList<Results.ResultsValue> productlist = new ArrayList<>();

    private static Map<String, List<String>> PLACES_BY_BEACONS;

    static{
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("55125:738", new ArrayList<String>() {{
            add("grocery");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            add("lifestyle");
            // "Green & Green Salads" is the next closest
            add("produce");
            // "Mini Panini" is the furthest away
        }});
        placesByBeacons.put("59599:33091", new ArrayList<String>() {{
            add("lifestyle");
            add("produce");
            add("grocery");
        }});
        placesByBeacons.put("1564:34409", new ArrayList<String>() {{
            add("produce");
            add("lifestyle");
            add("grocery");
        }});

        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
        apiService = APIClient.getClient().create(APIInterface.class);

        adapter = new discountAdapter(this, R.layout.product_info, productlist);

       
        // TODO: replace "<major>:<minor>" strings to match your own beacons.

        
      beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
          @Override
          public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {

              Beacon beacon1 = null;
              int max =0 ;

              if (!beacons.isEmpty()) {
                  Beacon nearestBeacon = beacons.get(0);
                  List<String> places = placesNearBeacon(nearestBeacon);
                  regionname = places.get(0);
                  System.out.println("region"  +regionname);

                  final ProductRequest productRequest = new ProductRequest();
                  productRequest.setRegion(places.get(0));

                  setAdapterforBeacon(productRequest);


              }

          /*    for(Beacon beacon:beacons){
*//*
                  if(beacon.getRssi()<max) {
                      max = beacon.getRssi();
                      beacon1 = beacon;
                  }
*//*

                 *//* if(beaconRegion.getProximityUUID() == beacon.getProximityUUID())
                  {


                  }
*//*              }*/

          }
      });

        
/*
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                if (!beaconRegion.getIdentifier().matches(regionname)) {
                    Toast.makeText(getApplicationContext(), "In range of " + beaconRegion.getIdentifier(), Toast.LENGTH_LONG).show();

                    regionname = beaconRegion.getIdentifier();

                    final ProductRequest productRequest = new ProductRequest();
                    productRequest.setRegion(beaconRegion.getIdentifier());
                    adapter = new discountAdapter( , R.layout.product_info, productlist );

                    setAdapterforBeacon(productRequest);
                    showNotification(
                            beaconRegion.getIdentifier(),
                            "Current security wait time is 15 minutes, "
                                    + "and it's a 5 minute walk from security to the gate. "
                                    + "Looks like you've got plenty of time!");

                }
            }
        });*/
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(new BeaconRegion(
                        "lifestyle",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        55125, 738));
                beaconManager.startMonitoring(new BeaconRegion(
                        "lifestyle",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        55125, 738));
                beaconManager.startRanging(new BeaconRegion(
                        "produce",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        59599, 33091));
                beaconManager.startRanging(new BeaconRegion(
                        "grocery",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        1564, 34409));
            }
        });


        beaconManager.setBackgroundScanPeriod(1100,1000);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }


    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
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

    void setAdapterforBeacon(ProductRequest productRequest) {
        Call<ArrayList<Results.ResultsValue>> call = apiService.getProducts(productRequest);
        call.enqueue(new Callback<ArrayList<Results.ResultsValue>>() {
            @Override
            public void onResponse(Call<ArrayList<Results.ResultsValue>> call, Response<ArrayList<Results.ResultsValue>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productlist = response.body();

                    System.out.println("yaha pe" + productlist.toString());
                    MainActivity.updatelistview();

                } else {
                    System.out.println("null in return");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<Results.ResultsValue>> call, Throwable t) {
                System.out.println("failed" + t.getLocalizedMessage());
            }
        });
    }
}

