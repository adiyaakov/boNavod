package com.marvelapp.myapplication.dialog_fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.activity.WorkerLookingActivity;
import com.marvelapp.myapplication.date_and_time.DatePicker;
import com.marvelapp.myapplication.date_and_time.TimePicker;
import com.marvelapp.myapplication.interfaces.OnDateSelected;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adi on 30/11/2016.
 */
public class DateTimePickerDialogFragment extends DialogFragment {

    private Context context;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button postBtn;
    private Date d;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.date_time_dialog_fragment,null);

        datePicker = (DatePicker)v.findViewById(R.id.datePickerDialog);
        timePicker = (TimePicker)v.findViewById(R.id.timePicerDialog);
        postBtn = (Button)v.findViewById(R.id.postBtnDialog);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                d = new Date(datePicker.getCal().get(Calendar.YEAR)-1900,
                        datePicker.getCal().get(Calendar.MONTH),
                        datePicker.getCal().get(Calendar.DAY_OF_MONTH),
                        timePicker.getCal().get(Calendar.HOUR_OF_DAY),
                        timePicker.getCal().get(Calendar.MINUTE));


                Date currentDate = new Date(System.currentTimeMillis());
                    if(d.getTime() - currentDate.getTime()  > 0) {
                        ((OnDateSelected) context).onDateSelected(dateToString(d),d.getTime());
                        Log.d("TAG", "time is: "+d.getTime());
                        //Toast.makeText(context, "תקין", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                else {
                        Toast.makeText(context, "תאריך לא תקין!", Toast.LENGTH_SHORT).show();
                        Log.d("TAG","testDate: "+ d.getTime());
                    }



            }
        });






        builder.setView(v);
        return builder.create();
    }




    private String dateToString(Date d){
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy HH:mm");

        String date = sf.format(d);
        return date;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        this.context = context;

    }

    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);





    }
}
