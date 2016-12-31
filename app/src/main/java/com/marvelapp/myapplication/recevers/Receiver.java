package com.marvelapp.myapplication.recevers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.activity.MainActivity;

/**
 * Created by Adi on 02/12/2016.
 */
public class Receiver extends BroadcastReceiver {
    private static int iText = 0;
    private int[] text = {R.string.app_reciver1,R.string.app_reciver2,R.string.app_reciver3};
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "active", Toast.LENGTH_SHORT).show();
        showNotification(context);
    }



    public void showNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 489, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.final_icon)
                .setContentTitle("עבודה מזדמנת")
                .setContentText(context.getResources().getString(text[iText])+"");
        if (iText<2){
            iText++;
        }
        if (iText==2){
            iText=0;
        }
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(444, mBuilder.build());
    }
}
