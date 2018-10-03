//package com.example.sunidhi.inclass04;
//
//import android.app.Application;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.widget.Toast;
//
//import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
//import com.estimote.coresdk.recognition.packets.Beacon;
//import com.estimote.coresdk.service.BeaconManager;
//
//import java.util.List;
//import java.util.UUID;
//
///**
// * Created by Sunidhi on 27-Sep-18.
// */
//
//public class MyApplication extends Application {
//    private BeaconManager beaconManager;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        beaconManager = new BeaconManager(getApplicationContext());
//
//        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
//            @Override
//            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
//                Toast.makeText( MyApplication.this, "Entered region", Toast.LENGTH_SHORT ).show();
//                showNotification(
//                        "Your gate closes in 47 minutes.",
//                        "Current security wait time is 15 minutes, "
//                                + "and it's a 5 minute walk from security to the gate. "
//                                + "Looks like you've got plenty of time!");
//            }
//            @Override
//            public void onExitedRegion(BeaconRegion region) {
//                Toast.makeText( MyApplication.this, "Exited region", Toast.LENGTH_SHORT ).show();
//                // could add an "exit" notification too if you want (-:
//            }
//        });
//
//        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
//            @Override
//            public void onServiceReady() {
//                beaconManager.startMonitoring(new BeaconRegion(
//                        "monitored region",
//                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
//                        1564, 34409));
//            }
//        });
//    }
//    public void showNotification(String title, String message) {
//        Intent notifyIntent = new Intent(this, MainActivity.class);
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
//                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification notification = new Notification.Builder(this)
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .build();
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notification);
//    }
//
//}
