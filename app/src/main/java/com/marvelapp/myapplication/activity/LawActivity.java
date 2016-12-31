package com.marvelapp.myapplication.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.marvelapp.myapplication.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class LawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);

        TextView txtRawResource= (TextView)findViewById(R.id.law_text);
        readRawTextFile(txtRawResource);


    }

    public void onBackClickedLawActivity(View view) {
        finish();
    }



    private void readRawTextFile(TextView view) {
        String data = "";
        StringBuilder sb = new StringBuilder();
        InputStream in = this.getResources().openRawResource(R.raw.new_law);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("NOTICE", "do not suppored encoding");
        }
        if (in != null) {
            try {
                while ((data = reader.readLine()) != null) {
                    sb.append(data + "\n");
                }
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        view.setText(sb.toString());
    }




}
