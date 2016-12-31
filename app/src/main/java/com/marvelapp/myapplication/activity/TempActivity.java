package com.marvelapp.myapplication.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.marvelapp.myapplication.Job;
import com.marvelapp.myapplication.MyRecyclerViewAdapter;
import com.marvelapp.myapplication.NewJob;
import com.marvelapp.myapplication.NewRecyclerViewAdapter;
import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.interfaces.OnRemovePost;
import com.marvelapp.myapplication.interfaces.OnRemovePostNew;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TempActivity extends Abstract_Activity implements OnRemovePostNew {
    private ArrayList<NewJob>jobs;
    private Firebase mRef;

    private RecyclerView rv;
    private boolean doQueryOrNot = false;
    private int areaPosition = -1;
    private TextView emptyRv;
    private String areaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_temp);
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


        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://marvelapp-20171.firebaseio.com/newJob/"+ areas[areaPosition]);
        //mRef = new Firebase("https://marvelapp-20171.firebaseio.com/sortList");


        for (int i = 0 ; i < 5 ; i++){

           // mRef.push().setValue(j);
        }
        jobs = new ArrayList<>();

        createListFromFireBase();









    }
    private void createListFromFireBase() {
        if (doQueryOrNot==false){
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    jobs.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        NewJob j = postSnapshot.getValue(NewJob.class);
                        Log.d("DATALONG", "LONG: "+ j.getJobDate());
                        Date d = new Date(j.getJobDate());
                        if(Calendar.getInstance().getTime().after(d)){
                            postSnapshot.getRef().removeValue();


                        }
                        else {
                            jobs.add(j);
                            Log.d("ADD", "job add well ");
                        }



                    }setAdapter(jobs);
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
                        NewJob j = postSnapshot.getValue(NewJob.class);

                        Date d = new Date(j.getJobDate());
                        if(Calendar.getInstance().getTime().after(d)){
                            postSnapshot.getRef().removeValue();

                            Log.d("DATALONG", "LONG: "+ j.getJobDate());
                        }
                        else {
                            jobs.add(j);
                            Log.d("ADD", "job add well ");
                        }




                    }setAdapter(jobs);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }


    }

    private void setAdapter(ArrayList<NewJob>jList){
        Collections.sort(jList, new Comparator<NewJob>(){
            @Override
            public int compare(NewJob o1, NewJob o2) {
                int a = (int) (o1.getJobDate()-o2.getJobDate());
                return a;
            }


        });
        NewRecyclerViewAdapter adapter = new NewRecyclerViewAdapter(jList,TempActivity.this,doQueryOrNot);
        rv.setAdapter(adapter);
        rv.setVisibility(View.VISIBLE);
        Log.d("TAG", "count: "+ adapter.getItemCount());
    }

    public void onBackClicked(View view) {
        finish();
    }


    @Override
    public void removePost(final NewJob job) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NewJob j = postSnapshot.getValue(NewJob.class);

                    Log.d("TAG", "job:: " + j.toString());

                    if (j.getCategory().equals(job.getCategory())&&
                            j.getTotalHours()==job.getTotalHours()&&
                            j.getJobDate()==(job.getJobDate())&&
                            j.isExperience()==job.isExperience()&&
                            j.getContactPhone().equals(job.getContactPhone())&&
                            j.getPart().equals(job.getPart())&&
                            j.getContactName().equals(job.getContactName())){
                        Log.d("TAG", "o++++++++++++++++++");


                        postSnapshot.getRef().removeValue();
                        jobs.clear();
                        Toast.makeText(TempActivity.this, "המודעה הוסרה בהצלחה!", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //finish();
        if (jobs.size()==1){
            finish();



        }

    }
}
