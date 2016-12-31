package com.marvelapp.myapplication.dialog_fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;

/**
 * Created by Adi on 28/11/2016.
 */
import android.view.Gravity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.marvelapp.myapplication.R;


public class InfoDialogFragment extends DialogFragment {
    private Button dismissBtn;

    private Context context;



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.info_dialog_fragment,null);


        dismissBtn = (Button)v.findViewById(R.id.dismissInfoDialog);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView info = (TextView)v.findViewById(R.id.infoTextView);
        info.setText(R.string.info);

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

