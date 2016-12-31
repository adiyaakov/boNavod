package com.marvelapp.myapplication.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;

import com.marvelapp.myapplication.dialog_fragment.InfoDialogFragment;
import com.marvelapp.myapplication.Job;
import com.marvelapp.myapplication.R;

/**
 * Created by Adi on 01/12/2016.
 */
public class BaseActivity extends Abstract_Activity {

    public Firebase firebase;
    public TextView law;
    public TextView info;
    public android.app.FragmentManager fragmentManager;



    @Override
    protected void onResume() {
        super.onResume();
        getDeviceId();


        law = (TextView) findViewById(R.id.lawTV);
        info = (TextView) findViewById(R.id.infoTV);

        

        fragmentManager = getFragmentManager();

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 InfoDialogFragment d = new InfoDialogFragment();
                 d.show(fragmentManager,"d");
            }
        });

        law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(BaseActivity.this,LawActivity.class);
            }
        });
    }


    public void createJob(Job j) {
        firebase = new Firebase("https://marvelapp-20171.firebaseio.com");
        firebase.child("newJob").push().setValue(j);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Firebase.setAndroidContext(this);

        firebase = new Firebase("https://marvelapp-20171.firebaseio.com/");

    }





}
