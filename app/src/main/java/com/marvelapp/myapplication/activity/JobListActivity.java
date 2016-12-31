package com.marvelapp.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.marvelapp.myapplication.Job;
import com.marvelapp.myapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JobListActivity extends Abstract_Activity {
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Job, ViewCatagoryHolder> queryedadapter, fulladapter;
    private TextView header, emptyRV;
    private int areaPosition;
    private String ref, area;
    int adapterItemCount = -100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);


        area = getIntent().getStringExtra("area");

        areaPosition = getIntent().getIntExtra("areaPosition",100);
        header = (TextView)findViewById(R.id.jobListHeader);


        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        emptyRV = (TextView)findViewById(R.id.activity_joblist_empty_text_view);
        appendText();






    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView.setVisibility(View.GONE);
        Firebase.setAndroidContext(this);


        ref = getIntent().getStringExtra("KEYCODE");

        mRef = new Firebase("https://marvelapp-20171.firebaseio.com/newJob");

        if (ref == null) {
            startFullAdapter();

        }

            else if (ref!=null){
            startQueryedadapter();
        }



            }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void startFullAdapter(){
        fulladapter = new FirebaseRecyclerAdapter<Job, ViewCatagoryHolder>(
                Job.class,
                R.layout.card_layout,
                ViewCatagoryHolder.class,
                mRef.child(areas[areaPosition])

        ) {
            @Override
            protected void populateViewHolder(ViewCatagoryHolder viewCatagoryHolder, final Job job, int i) {
                viewCatagoryHolder.trash_btn.setVisibility(View.INVISIBLE);
                viewCatagoryHolder.category.setText(" תחום:"+" " +job.getCategory());
                viewCatagoryHolder.address.setText("מיקום מדויק: " +job.getAddress());
                viewCatagoryHolder.contact.setText("איש קשר: " + " "+ job.getContactName() + " " + job.getContactPhone());
                viewCatagoryHolder.hours.setText("שעות עבודה:"+" " +job.getTotalHours());
                viewCatagoryHolder.part.setText("תפקיד: "+" " +job.getPart());
                viewCatagoryHolder.date.setText("תאריך ושעה:"+" " +job.getJobDate());

                if (job.isExperience()==true){
                    viewCatagoryHolder.experience.setText("ניסיון:"+" " + "דורש ניסיון");
                }
                if (job.isExperience()==false){
                    viewCatagoryHolder.experience.setText("ניסיון:"+" " + "לא נדרש ניסיון");
                }

                viewCatagoryHolder.contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + job.getContactPhone()));
                        startActivity(intent);
                    }
                });

                if (compareDate(convertStringToDate(job.getJobDate()))!=true){
                    fulladapter.getRef(i).removeValue();

                }




            }
        };


        fulladapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(fulladapter);

            }


        });
    }

    private void appendText(){
        String temp = getIntent().getStringExtra("KEYCODE");
        String text;
        if (temp==null) {
            text = getString(R.string.empty_list);
            header.setText(getResources().getString(R.string.heafer_full_post)+ " " + area);
        }
        else {
            text = getString(R.string.empty_list2);
            header.setText(getResources().getString(R.string.header_my_post) + " "+ area);
        }
        emptyRV.setText(text);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        emptyRV.setAnimation(animation);
    }

    private void startQueryedadapter(){
        queryedadapter = new FirebaseRecyclerAdapter<Job, ViewCatagoryHolder>(
                Job.class,
                R.layout.card_layout,
                ViewCatagoryHolder.class,
                mRef.child(areas[areaPosition]).orderByChild("deviceID").equalTo(getDeviceId())

        ) {
            @Override
            protected void populateViewHolder(ViewCatagoryHolder viewCatagoryHolder, final Job job, final int i) {
                viewCatagoryHolder.trash_btn.setVisibility(View.VISIBLE);
                viewCatagoryHolder.trash_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        queryedadapter.getRef(i).removeValue();
                    }
                });
                viewCatagoryHolder.category.setText(" תחום:"+" " +job.getCategory());
                viewCatagoryHolder.address.setText("מיקום מדויק: " +job.getAddress());
                viewCatagoryHolder.contact.setText("איש קשר: " + " "+ job.getContactName() + " " + job.getContactPhone());

                viewCatagoryHolder.hours.setText("שעות עבודה:"+" " +job.getTotalHours());
                viewCatagoryHolder.part.setText("תפקיד: "+" " +job.getPart());
                viewCatagoryHolder.date.setText("תאריך ושעה:"+" " +job.getJobDate());


                if (job.isExperience()==true){
                    viewCatagoryHolder.experience.setText("ניסיון:"+" " + "דורש ניסיון");
                }
                if (job.isExperience()==false){
                    viewCatagoryHolder.experience.setText("ניסיון:"+" " + "לא נדרש ניסיון");
                }
                if (compareDate(convertStringToDate(job.getJobDate()))!=true){
                    queryedadapter.getRef(i).removeValue();
                }





            }
        };  queryedadapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(queryedadapter);

            }


        });


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

    public void onBackClicked(View view) {
        finish();
    }



}



