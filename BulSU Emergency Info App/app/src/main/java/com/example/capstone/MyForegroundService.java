package com.example.capstone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.Messaging.chat;
import com.example.capstone.login.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyForegroundService extends Service {
    int counter,size;
    int counter2,size2;
    String id;
    int req;
    int req2;
    private static final int NOTIFICATION_ID = 123;
    private static final long TASK_INTERVAL_MS = 5000; // 5 seconds
    private Handler handler = new Handler();
    private final Runnable taskRunnable = new Runnable() {
        @Override
        public void run() {
            if(id.equals("admin")){
                new MyWorkerAsync2(MyForegroundService.this).execute(id);
            }
            new MyWorkerAsync(MyForegroundService.this).execute(id);
            // Schedule the task to run again after 5 seconds
            handler.postDelayed(this, TASK_INTERVAL_MS);
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra("id")){
            id = intent.getStringExtra("id");
            handler.post(taskRunnable);
        }
        if(intent.hasExtra("stop")){

            stopMyForegroundService();
        }
        return START_NOT_STICKY;
    }
    private void stopMyForegroundService() {
        // Remove the foreground state and notification
        stopForeground(true);

        // Stop the service itself
        stopSelf();
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(taskRunnable);
        super.onDestroy();
        // Clean up resources and stop the service
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void updates2(ArrayList<String> messages, ArrayList<String> sender) throws IOException{
        if (counter2!=1){
            size2 = messages.size();
            counter2=1;
        }
        else if(size2!=messages.size()){
            size2 = messages.size();
            String s = sender.get(size2-1);
            Intent intent = new Intent(getApplicationContext(), AdminReports.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            if(isStringInteger(s)){
                req2 = Integer.parseInt(s);
            }
            else{
                req2 = 694202;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), req2,intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://com.example.capstone/raw/msg"));
            mediaPlayer.prepare();
            mediaPlayer.start();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"chat")
                    .setSmallIcon(R.drawable. message )
                    .setContentTitle(s).setSound(null).setAutoCancel(true)
                    .setContentText("sent a report about "+messages.get(messages.size()-1)).setContentIntent(pendingIntent) ;
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {

                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( "chat" , "CHAT" , importance) ;
                notificationChannel.enableLights( true ) ;
                mBuilder.setChannelId( "chat" ) ;
                notificationChannel.setSound(null,null);
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(notificationChannel) ;
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                    mBuilder.build()) ;

        }
    }
    public void updates(ArrayList<String> messages, ArrayList<String> sender) throws IOException {
        if (counter!=1){
            size = messages.size();
            counter=1;
        }
        else if(size!=messages.size()){
            size = messages.size();
            String s;
            Intent intent = new Intent(getApplicationContext(), chat.class);
            if(sender.get(size-1).equals("admin")){
                s = "Admin";
            }
            else{
                s = sender.get(size-1);
            }
            intent.putExtra("name",s);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            if(isStringInteger(s)){
                req = Integer.parseInt(s);
            }
            else{
                req = 69420;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), req,intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://com.example.capstone/raw/msg"));
            mediaPlayer.prepare();
            mediaPlayer.start();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"chat")
                    .setSmallIcon(R.drawable. message )
                    .setContentTitle(s).setSound(null).setAutoCancel(true)
                    .setContentText(messages.get(messages.size()-1)).setContentIntent(pendingIntent) ;
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {

                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( "chat" , "CHAT" , importance) ;
                notificationChannel.enableLights( true ) ;
                mBuilder.setChannelId( "chat" ) ;
                notificationChannel.setSound(null,null);
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(notificationChannel) ;
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                    mBuilder.build()) ;

        }
    }
    private Notification createNotification() {


        // Create a NotificationChannel (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "your_channel_id",
                    "Foreground Service",
                    NotificationManager.IMPORTANCE_MIN
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Build and return the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "your_channel_id")
                .setSmallIcon(R.drawable.asdfg)
                .setContentTitle("BulSU Emergency Info App")
                .setContentText("App is running in background")
                .setPriority(NotificationCompat.PRIORITY_MIN)

                .setAutoCancel(false).setOngoing(true);

        return builder.build();
    }

    public boolean isStringInteger(String str) {
        try {
            Integer.parseInt(str);
            // If the parsing succeeds, the string is an integer
            return true;
        } catch (NumberFormatException e) {
            // If an exception is thrown, the string is not an integer
            return false;
        }
    }
}
