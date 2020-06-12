package com.universalapp.sankalp.learningapp.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.view.activities.MainActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        //sendMyNotification(message);
        //Crashlytics.setString("Notification "+Build.VERSION.SDK_INT, message.getNotification().getBody());

        try {
            Map<String, String> notifiationData = message.getData();
            System.out.println("Notification-" + notifiationData.get("body"));
            sendMyNotification(notifiationData);
            Crashlytics.setString("Notification "+Build.VERSION.SDK_INT, notifiationData.toString());
        }catch (Exception e){
            System.out.println("notification catch- "+e.getLocalizedMessage());
        }
    }

    private void sendMyNotification(RemoteMessage message) {

        try {
            //JSONObject jsonObject = new JSONObject(message.getNotification().getBody());
            String messageText = message.getNotification().getBody();
            Uri link = message.getNotification().getLink();
            String image = message.getNotification().getImageUrl()+"";
            String title = message.getNotification().getTitle();
            Intent intent;
            if(link == null || link.toString().matches("") ){
                intent = new Intent(this, MainActivity.class);
            }else{
                /*intent = new Intent(this, MainActivity.class);
                intent.putExtra("link", link.toString());*/
                intent = new Intent(Intent.ACTION_VIEW).setData(link);

                //intent.setData(link);
            }
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntentWithParentStack(intent);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder;
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("sankalp", "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                //notificationManager.createNotificationChannel(channel);
                notificationBuilder = new NotificationCompat.Builder(this, "sankalp");
                if (image != null) {
                    Bitmap bitmap;
                    bitmap = getBitmapfromUrl(image);
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(messageText)
                            .setAutoCancel(true)
                            .setSound(soundUri)
                            .setLargeIcon(bitmap)
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(bitmap)
                                    .bigLargeIcon(null))
                            .setContentIntent(pendingIntent);
                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    NotificationChannel mChannel = new NotificationChannel("sankalp", "sankalp", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(mChannel);
                    manager.notify(1234, notificationBuilder.build());
                } else {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(messageText)
                            .setAutoCancel(true)
                            .setSound(soundUri)
                            .setContentIntent(pendingIntent);
                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    NotificationChannel mChannel = new NotificationChannel("sankalp", "sankalp", NotificationManager.IMPORTANCE_DEFAULT);
                    manager.createNotificationChannel(mChannel);
                    manager.notify(12345, notificationBuilder.build());
                }
            }else {
                notificationBuilder = new NotificationCompat.Builder(this);
                if (image != null) {
                    Bitmap bitmap;
                    bitmap = getBitmapfromUrl(image);
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(messageText)
                            .setAutoCancel(true)
                            .setSound(soundUri)
                            .setLargeIcon(bitmap)
                            .setChannelId("sankalp")
                            .setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigPicture(bitmap)
                                    .bigLargeIcon(null))
                            .setContentIntent(pendingIntent);
                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(1234, notificationBuilder.build());
                } else {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(messageText)
                            .setAutoCancel(true)
                            .setSound(soundUri)
                            .setChannelId("sankalp")
                            .setContentIntent(pendingIntent);
                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(12345, notificationBuilder.build());
                }
            }



        }catch (Exception e){
            System.out.println("Notification  catch- "+e.getLocalizedMessage());
        }

    }
    private void sendMyNotification(Map<String, String> message) {

         try {
             //JSONObject jsonObject = new JSONObject(message.get("body"));
             String messageText = message.get("body");
             String link = message.get("link");
             String image = message.get("image");
             String title = message.get("title");
             Intent intent;
             if(link == null){
                 intent = new Intent(this, MainActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
             }else{
                 intent = new Intent(Intent.ACTION_VIEW);
                 intent.setData(Uri.parse(link));
             }

             PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

             Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

             if (image != null) {
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                     Bitmap bitmap;
                     bitmap = getBitmapfromUrl(image);
                     Notification notification = new NotificationCompat.Builder(this, "sankalp")
                             .setContentTitle(title)
                             .setContentText(messageText)
                             .setSmallIcon(R.mipmap.ic_launcher)
                             .setLargeIcon(bitmap)
                             .setStyle(new NotificationCompat.BigPictureStyle()
                                     .bigPicture(bitmap)
                                     .bigLargeIcon(null))
                             .setAutoCancel(true)
                             .setSound(soundUri)
                             .setContentIntent(pendingIntent)
                             .build();
                     NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                     manager.notify(123, notification);
                 }else{
                     /*NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "sankalp")
                             .setContentTitle(title)
                             .setContentText(messageText)
                             .setSmallIcon(R.mipmap.ic_launcher_foreground)
                             .setAutoCancel(true)
                             .setSound(soundUri)
                             .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                             .setContentIntent(pendingIntent).setChannelId("sankalp");
                     NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                     manager.notify(123, notification.build());*/
                     NotificationCompat.Builder notificationBuilder;
                     notificationBuilder = new NotificationCompat.Builder(this);
                     if (image != null) {
                         Bitmap bitmap;
                         bitmap = getBitmapfromUrl(image);
                         notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                                 .setContentTitle(title)
                                 .setContentText(messageText)
                                 .setAutoCancel(true)
                                 .setSound(soundUri)
                                 .setLargeIcon(bitmap)
                                 .setChannelId("sankalp")
                                 .setStyle(new NotificationCompat.BigPictureStyle()
                                         .bigPicture(bitmap)
                                         .bigLargeIcon(null))
                                 .setContentIntent(pendingIntent);
                         NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                         manager.notify(1234, notificationBuilder.build());
                     } else {
                         notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                                 .setContentTitle(title)
                                 .setContentText(messageText)
                                 .setAutoCancel(true)
                                 .setSound(soundUri)
                                 .setChannelId("sankalp")
                                 .setContentIntent(pendingIntent);
                         NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                         manager.notify(12345, notificationBuilder.build());
                     }
                 }

             } else {
                 Notification notification = new NotificationCompat.Builder(this, "sankalp")
                         .setContentTitle(title)
                         .setContentText(messageText)
                         .setSmallIcon(R.mipmap.ic_launcher_round)
                         .setAutoCancel(true)
                         .setSound(soundUri)
                         .setContentIntent(pendingIntent)
                         .build();
                 NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                 manager.notify(123, notification);
             }





        }catch (Exception e){
             System.out.println("Notification  catch- "+e.getLocalizedMessage());
        }

    }

    private Bitmap getBitmapfromUrl(String url){
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch(IOException e) {
            System.out.println(e);
            return null;
        }
    }
}