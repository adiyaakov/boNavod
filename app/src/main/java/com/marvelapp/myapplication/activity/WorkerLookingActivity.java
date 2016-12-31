package com.marvelapp.myapplication.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
/*
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;*/
import com.google.android.gms.common.api.GoogleApiClient;
import com.marvelapp.myapplication.Job;
import com.marvelapp.myapplication.NewJob;
import com.marvelapp.myapplication.dialog_fragment.AreaPickerDialogFragmet;
import com.marvelapp.myapplication.dialog_fragment.DateTimePickerDialogFragment;
import com.marvelapp.myapplication.interfaces.OnAreaSelcted;
import com.marvelapp.myapplication.interfaces.OnDateSelected;
import com.marvelapp.myapplication.R;

public class WorkerLookingActivity extends BaseActivity implements OnDateSelected, OnAreaSelcted {
    private Button post, date, area;
    private CheckBox checkBox;
    private EditText hours, address, part, contacrName, contactPhoneNumber,category;
    private ToggleButton experience;
    private String dateButtonText, areaButtonText;
    private int areaPosition;
    private long chosenDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.temp_worker_looking);


        date = (Button) findViewById(R.id.workerLookingDate);
        area = (Button) findViewById(R.id.workerLookingArea);
        post = (Button) findViewById(R.id.workerLookingPostBtn);
        checkBox = (CheckBox) findViewById(R.id.workerLookingCheckBox);

        contacrName = (EditText) findViewById(R.id.workerLookingContactName);
        contactPhoneNumber = (EditText) findViewById(R.id.workerLookingContactNumber);
        hours = (EditText) findViewById(R.id.workerLookingNumOfHours);
        address = (EditText) findViewById(R.id.workerLookingAddress);
        category = (EditText)findViewById(R.id.workerLookingCategory);
        part = (EditText) findViewById(R.id.workerLookingPart);
        experience = (ToggleButton) findViewById(R.id.workerLookingExperience);

        dateButtonText = date.getText().toString();
        areaButtonText = area.getText().toString();

        TextView checkBoxText = (TextView)findViewById(R.id.checkBoxtext);
        checkBoxText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity(WorkerLookingActivity.this,LawActivity.class);
            }
        });



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkBox.isChecked() == true && hours.getText().length() > 0
                        && contacrName.getText().length()>1
                        && category.getText().length()>=3
                        && address.getText().length() > 0 && part.getText().length() > 0
                        && !date.getText().toString().equals(dateButtonText)
                        && !area.getText().toString().equals(areaButtonText)) {

                        int phoneNumberLenget = contactPhoneNumber.getText().toString().trim().length();
                    if (phoneNumberLenget == 10 || phoneNumberLenget == 11 || phoneNumberLenget == 9) {


                        NewJob j = new NewJob(getDeviceId(), category.getText().toString(),
                                part.getText().toString(), address.getText().toString()
                                , contacrName.getText().toString()
                                , contactPhoneNumber.getText().toString()
                                , experience.isChecked(), chosenDate,
                                Integer.parseInt(hours.getText().toString()));
                        firebase.child("newJob").child(areas[areaPosition]).push().setValue(j);
                        Toast.makeText(WorkerLookingActivity.this, "המודעה פורסמה בהצלחה", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                    else {contactPhoneNumber.setError("מספר לא תקין");}
                }
                    else {
                    post.setError("אנא מלא את כל השדות");
                    if (contacrName.getText().length() <= 1){
                        contacrName.setError("שדה קצר מידי");
                    }
                    if (category.getText().length() <=3){
                        category.setError("שדה קצר מידי");
                    }
                    Toast.makeText(WorkerLookingActivity.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                }
            }
        });

        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreaPickerDialogFragmet a = new AreaPickerDialogFragmet();
                Bundle b = new Bundle();
                b.putInt("color",4);
                a.setArguments(b);
                a.show(fragmentManager, "q");
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerDialogFragment d = new DateTimePickerDialogFragment();
                d.show(fragmentManager, "d");


            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        law.setTextColor((getResources().getColor(R.color.white)));
    }

    @Override
    public void onDateSelected(String selectedDate, long chosen) {
        date.setText(selectedDate);
        this.chosenDate = chosen;

    }

    @Override
    public void onAreaSelected(String areaSelected, int position) {
        area.setText(areaSelected);
        areaPosition = position;
    }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
