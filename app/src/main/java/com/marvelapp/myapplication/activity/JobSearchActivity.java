package com.marvelapp.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marvelapp.myapplication.R;
import com.marvelapp.myapplication.dialog_fragment.AreaPickerDialogFragmet;
import com.marvelapp.myapplication.interfaces.OnAreaSelcted;

public class JobSearchActivity extends BaseActivity implements OnAreaSelcted {
    private Button search,chooseArea;
    private int areaPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        chooseArea = (Button)findViewById(R.id.buttonChooseArea);
        search = (Button)findViewById(R.id.buttonStartSearch);

        final String areaButtonText = chooseArea.getText().toString();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!areaButtonText.equals(chooseArea.getText().toString())) {
                    /*Intent i = new Intent(JobSearchActivity.this, JobListActivity.class);
                    i.putExtra("area", chooseArea.getText().toString());
                    i.putExtra("areaPosition",areaPosition);
                    startActivity(i);*/

                    Intent i = new Intent(JobSearchActivity.this,TempActivity.class);
                    //Intent i = new Intent(JobSearchActivity.this,SortListActivity.class);
                    i.putExtra("doQueryOrNot",false);//false means do-not query from full list;
                    i.putExtra("area", chooseArea.getText().toString());// PUT WHICH AREA IS SELECTED, LIKE CENTER/JERUSALEM
                    i.putExtra("areaPosition",areaPosition);//EVERY AREA HAS A PLACE IN ARRAY, THE POSITION IS THE INDEX
                    startActivity(i);
                }
                else {
                    chooseArea.setError("אנא בחר אזור");
                }

            }
        });



        chooseArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AreaPickerDialogFragmet a = new AreaPickerDialogFragmet();
                Bundle b = new Bundle();
                b.putInt("color",-1);
                a.setArguments(b);
                a.show(fragmentManager,"a");
            }
        });
    }

    @Override
    public void onAreaSelected(String areaSelected, int position) {
        chooseArea.setError(null);
        chooseArea.setText(areaSelected);
        areaPosition = position;
    }

    @Override
    protected void onResume() {
        super.onResume();
        info.setTextColor((getResources().getColor(R.color.mainSKY)));
    }
}
