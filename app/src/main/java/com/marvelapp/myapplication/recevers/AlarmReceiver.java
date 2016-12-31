package com.marvelapp.myapplication.recevers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.activity.MainActivity;

/**
 * Created by Adi on 20/12/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static int iText = 0;
    private int[] text = {R.string.app_reciver1,R.string.app_reciver2,R.string.app_reciver3};
    private static int MID;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub


        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.final_icon)
                .setContentTitle("עבודה מזדמנת")
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentText(context.getResources().getString(text[iText])+"");
        if (iText<2){
            iText++;
        }
        if (iText==2){
            iText=0;
        }

        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true).setWhen(when);
        notificationManager.notify(MID, mBuilder.build());
        MID++;

    }

}
