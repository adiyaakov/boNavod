package com.marvelapp.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.dialog_fragment.AreaPickerDialogFragmet;
import com.marvelapp.myapplication.interfaces.OnAreaSelcted;
import com.marvelapp.myapplication.recevers.Receiver;

public class MainActivity extends BaseActivity implements OnAreaSelcted {
    private RelativeLayout left,right;
    private final int NOTIFICATION_CODE = 444;
    private TextView workerTV,jobTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            sendNotification2(this);
            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }




        left = (RelativeLayout)findViewById(R.id.relativeLeft);

        right = (RelativeLayout)findViewById(R.id.relativeRight);



        workerTV = (TextView) findViewById(R.id.buttonWorkerLooking);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(MainActivity.this,JobSearchActivity.class);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(MainActivity.this,WorkerLookingActivity.class);
            }
        });

        jobTV = (TextView) findViewById(R.id.buttonJobLooking);

        jobTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(MainActivity.this,JobSearchActivity.class);
            }
        });

        workerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     openNewActivity(MainActivity.this,WorkerLookingActivity.class);
            }
        });

    }






    public void openQueryList(View view) {
        AreaPickerDialogFragmet a = new AreaPickerDialogFragmet();
        Bundle b = new Bundle();
        b.putInt("color",4);
        a.setArguments(b);
        a.show(fragmentManager,"a");
        //after choose area in dialog the method onAreaSelected will call by implemet in dialog

    }

    @Override
    public void onAreaSelected(String areaSelected, int position) {
        /*Intent i = new Intent(this,JobListActivity.class);
        i.putExtra("area", areaSelected);
        i.putExtra("KEYCODE","a");
        i.putExtra("areaPosition",position);
        startActivity(i);*/

        //Intent i = new Intent(MainActivity.this,SortListActivity.class);
        Intent i = new Intent(MainActivity.this,TempActivity.class);
        i.putExtra("doQueryOrNot",true);//true means do query from full list;
        i.putExtra("area", areaSelected);// PUT WHICH AREA IS SELECTED, LIKE CENTER/JERUSALEM
        i.putExtra("areaPosition",position);//EVERY AREA HAS A PLACE IN ARRAY, THE POSITION IS THE INDEX
        startActivity(i);
    }


}
