package com.marvelapp.myapplication.dialog_fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.interfaces.OnAreaSelcted;


/**
 * Created by Adi on 01/12/2016.
 */
public class AreaPickerDialogFragmet extends DialogFragment {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Context context;
    private String[] arr = {"צפון","חיפה","שרון","מרכז","ירושלים והסביבה",
            "שומרון","שפלה","דרום","אילת והסביבה"};




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v;
        int layRes;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(getArguments().getInt("color")==4){

            v = LayoutInflater.from(getActivity()).inflate(R.layout.area_dialig_two,null);
            layRes = R.layout.list_row_2;
        }
        else {
            v = LayoutInflater.from(getActivity()).inflate(R.layout.area_picker_dialog_fragment,null);
            layRes = R.layout.listrow;
        }
        listView = (ListView)v.findViewById(R.id.areasList);



            adapter = new ArrayAdapter(context, layRes, R.id.dele, arr);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((OnAreaSelcted)context).onAreaSelected(adapter.getItem(position),position);
                dismiss();
            }
        });



        builder.setView(v);
        return builder.create();
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
