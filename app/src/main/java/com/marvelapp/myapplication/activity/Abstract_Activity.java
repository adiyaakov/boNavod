package com.marvelapp.myapplication.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.marvelapp.myapplication.Job;
import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.recevers.AlarmReceiver;
import com.marvelapp.myapplication.recevers.Receiver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adi on 02/12/2016.
 */
public class Abstract_Activity extends AppCompatActivity {


    public Firebase mRef;
    public String[]areas = {"nor","hai","sha","cen","jer","sho","shfe","sou","eil"};


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    public void sendNotification2(Context context){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 4);
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);


        Intent intent1 = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
    }



    public String getDeviceId(){
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("ID", "getDeviceId:   " + android_id);
        return android_id;
    }

    public void openNewActivity(Context c, Class TargetClass){
        Intent i = new Intent(c,TargetClass);
        startActivity(i);
    }

    public long getCurrentDateAndTime(){
        return System.currentTimeMillis();
    }






}


