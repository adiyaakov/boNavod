package com.marvelapp.myapplication.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.marvelapp.myapplication.Job;
import com.marvelapp.myapplication.MyRecyclerViewAdapter;
import com.marvelapp.myapplication.NewJob;
import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.interfaces.OnRemovePost;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class SortListActivity extends Abstract_Activity implements OnRemovePost {
    private Firebase mRef;
    private ArrayList<Job>jobsList;
    private RecyclerView rv;
    private boolean doQueryOrNot = false;
    private int areaPosition = -1;
    private TextView emptyRv;
    private String areaSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);

        doQueryOrNot = getIntent().getBooleanExtra("doQueryOrNot",false);
        areaPosition = getIntent().getIntExtra("areaPosition",-1);
        areaSelected = getIntent().getStringExtra("area");


        emptyRv = (TextView)findViewById(R.id.activity_joblist_empty_text_view);
        TextView header = (TextView)findViewById(R.id.newJobListHeader);
        if (doQueryOrNot==false){
            header.setText(getResources().getString(R.string.heafer_full_post)+ " " + areaSelected);
        }
        if (doQueryOrNot==true){
            header.setText(getResources().getString(R.string.header_my_post) + " "+ areaSelected);
        }


        rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setHasFixedSize(true);

        jobsList = new ArrayList<>();
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://marvelapp-20171.firebaseio.com/newJob/"+ areas[areaPosition]);


        createListFromFireBase();




    }

    private void createListFromFireBase() {
        if (doQueryOrNot==false){
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    jobsList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Job j = postSnapshot.getValue(Job.class);

                        if(compareDate(convertStringToDate(j.getJobDate()))!=true){
                            postSnapshot.getRef().removeValue();

                            Log.d("DATALONG", "LONG: "+ j.getJobDate());
                        }
                        else {
                            jobsList.add(j);
                            Log.d("ADD", "job add well ");
                        }



                    }setAdapter(jobsList, false);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        if (doQueryOrNot==true){

            mRef.orderByChild("deviceID").equalTo(getDeviceId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Job j = postSnapshot.getValue(Job.class);

                        if(compareDate(convertStringToDate(j.getJobDate()))!=true){
                            postSnapshot.getRef().removeValue();


                        }
                        else {
                            jobsList.add(j);
                            Log.d("ADD", "job add well ");
                        }




                    }setAdapter(jobsList,false);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }




    }
    private ArrayList<Job> sortArrayList(ArrayList<Job> arr){


       Collections.sort(arr, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                Long l1 = convertStringToDate(o1.getJobDate()).getTime();
                Long l2 = convertStringToDate(o2.getJobDate()).getTime();

                return ((int)(l1-l2));

            }
        });

        return arr;
    }



    private boolean compareDate(Date date){
        boolean b = false;

        if(date.after(Calendar.getInstance().getTime())){
            Log.d("compareDate","Date>currentDate");
            b = true; /* currentdate is bigger than the job date so remove
            the post from the list*/

        }
        else Log.d("compareDate","currentdate>Date");

        return b;
    }

    private Date convertStringToDate(String date){


        Date d = null;
        DateFormat sf = new SimpleDateFormat("dd/MM/yy HH:mm");
        try {

            d = sf.parse(date);
            Log.d("CONVERTSTRING", "try convert string");

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("CONVERTSTRING", "fail convert string");
        }
        return d;
    }

    private void setAdapter(ArrayList<Job>jobs, boolean swapAdapter){
        ArrayList<Job>list = sortArrayList(jobs);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(list,SortListActivity.this,doQueryOrNot);
        /*final AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(2000);*/

        if (adapter.getItemCount()>0) {
            rv.setVisibility(View.VISIBLE);
            //rv.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
            if (swapAdapter==false) {
                rv.setAdapter(adapter);
            }
            else {
                rv.swapAdapter(adapter,false);
            }
        }
        else {
            String text;
            if (doQueryOrNot==false){
                text = getString(R.string.empty_list);

            }
            else {
                text = getString(R.string.empty_list2);
            }
            emptyRv.setText(text);
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
            emptyRv.setAnimation(animation);
        }


    }


    public void onBackClicked(View view) {
        finish();
    }

    @Override
    public void removePost(final Job job) {



        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Job j = postSnapshot.getValue(Job.class);

                    Log.d("TAG", "job:: " + j.toString());

                    if (j.getCategory().equals(job.getCategory())&&
                            j.getTotalHours()==job.getTotalHours()&&
                            j.getJobDate().equals(job.getJobDate())&&
                            j.isExperience()==job.isExperience()&&
                            j.getJobDate().equals(job.getJobDate())&&
                            j.getContactPhone().equals(job.getContactPhone())&&
                            j.getPart().equals(job.getPart())&&
                            j.getContactName().equals(job.getContactName())){
                        Log.d("TAG", "o++++++++++++++++++");


                        postSnapshot.getRef().removeValue();
                        jobsList.clear();
                        Toast.makeText(SortListActivity.this, "המודעה הוסרה בהצלחה!", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //finish();
        if (jobsList.size()==1){
            finish();



        }
    }
}
