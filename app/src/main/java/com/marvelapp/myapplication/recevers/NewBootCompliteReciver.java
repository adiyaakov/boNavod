package com.marvelapp.myapplication.recevers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adi on 28/12/2016.
 */

public class NewBootCompliteReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, AlarmReceiver.class);


        Calendar calendar = Calendar.getInstance();
        Calendar now = calendar;
        Log.d("DATE","TIME" + calendar.getTime().toString());
        calendar.set(Calendar.DAY_OF_WEEK, 4);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);


        /*if (comapreDates(calendar,now)==false) {
            Log.d("DATE", "onReceive: "+ " IN!!!");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        }*/

            Log.d("DATEISON", "onReceive: "+ " FINE!!!");
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pIntent);


           /* am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7,
                    PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT));*/

    }


}
