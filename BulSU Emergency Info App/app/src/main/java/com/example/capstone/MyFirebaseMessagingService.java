package com.example.capstone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;
import com.example.capstone.Messaging.chat;
import com.example.capstone.Messaging.chatAsync;
import com.example.capstone.login.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.example.capstone.user.UserPage;

import java.io.IOException;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        if (remoteMessage.getNotification() != null) {

        }
        try {
            sendNotification(remoteMessage.getNotification().getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(String body) throws IOException {
        Intent intent = new Intent(this, BulsuEvacPlan.class);
        intent.putExtra("msg", body);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://com.example.capstone/raw/alert3"));
        mediaPlayer.prepare();
        mediaPlayer.start();
        String channelId = "fcm_default_channel";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable. bells )
                .setContentTitle( "ALERT" ).
                setContentIntent(pendingIntent).
                setContentText(body).
        setSound(null).setAutoCancel(true).
                setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body)) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {

            int importance = NotificationManager. IMPORTANCE_MAX ;
            NotificationChannel notificationChannel = new NotificationChannel( channelId , "ALERT" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            mBuilder.setChannelId( channelId ) ;
            notificationChannel.setSound(null,null);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }

}
