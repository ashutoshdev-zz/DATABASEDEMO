package gcm.com.gcmapplication;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;



public class MSGService extends IntentService {

    SharedPreferences prefs;
    NotificationCompat.Builder notification;
    NotificationManager manager;


    public MSGService() {
        super("MSGService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        prefs = getSharedPreferences("Chat", 0);


        if (!extras.isEmpty()) {

            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.e("L2C","Error");

            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.e("L2C","Error");

            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                Log.e("TAG", "Received: " + extras.getString("message"));
                    sendNotification(extras.getString("message"));


            }
        }
       // MSGReceiver.completeWakefulIntent(intent);
    }




    private void sendNotification(String msg) {

        Bundle args = new Bundle();
        args.putString("message", msg);
        Intent chat = new Intent(this, MainActivity.class);
        chat.putExtra("INFO", args);
        notification = new NotificationCompat.Builder(this);
        notification.setContentText(msg);
        notification.setTicker("New Message !");
        notification.setSmallIcon(R.drawable.ic_launcher);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 1000,
                chat, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        notification.setAutoCancel(true);
        manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
    }


}