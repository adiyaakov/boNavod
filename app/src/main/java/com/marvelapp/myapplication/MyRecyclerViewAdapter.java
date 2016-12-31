package com.marvelapp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.marvelapp.myapplication.activity.ViewCatagoryHolder;
import com.marvelapp.myapplication.interfaces.OnRemovePost;

import java.util.ArrayList;

/**
 * Created by Adi on 24/12/2016.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CardHolder> {
    private ArrayList<Job> mList;
    private Context mContext;
    private boolean showTrashBtn = false;

    public MyRecyclerViewAdapter(ArrayList<Job> mList, Context mContext, boolean showTrashBtn) {
        this.mList = mList;
        this.mContext = mContext;
        this.showTrashBtn = showTrashBtn;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, null);
        CardHolder viewHolder = new CardHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardHolder viewCatagoryHolder, final int position) {
        final Job job = mList.get(position);

        if (showTrashBtn==false){
            viewCatagoryHolder.trash_btn.setVisibility(View.INVISIBLE);
        }
        else {viewCatagoryHolder.trash_btn.setVisibility(View.VISIBLE);}

        viewCatagoryHolder.trash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnRemovePost)(mContext)).removePost(job);
            }
        });

        viewCatagoryHolder.category.setText(" תחום:" + " " + job.getCategory());
        viewCatagoryHolder.address.setText("מיקום מדויק: " + job.getAddress());
        viewCatagoryHolder.contact.setPaintFlags(viewCatagoryHolder.contact.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        viewCatagoryHolder.contact.setText("איש קשר: " + " " + job.getContactName() + " " + job.getContactPhone());
        viewCatagoryHolder.hours.setText("שעות עבודה:" + " " + job.getTotalHours());
        viewCatagoryHolder.part.setText("תפקיד: " + " " + job.getPart());
        viewCatagoryHolder.date.setText("תאריך ושעה:" + " " + job.getJobDate());

        if (job.isExperience() == true) {
            viewCatagoryHolder.experience.setText("ניסיון:" + " " + "דורש ניסיון");
        }
        if (job.isExperience() == false) {
            viewCatagoryHolder.experience.setText("ניסיון:" + " " + "לא נדרש ניסיון");
        }

        viewCatagoryHolder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + job.getContactPhone()));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CardHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView category, part, experience, hours, date, address, contact;
        Button trash_btn;

        public CardHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card);
            trash_btn = (Button) itemView.findViewById(R.id.trash_btn);
            category = (TextView) itemView.findViewById(R.id.cardCategory);
            part = (TextView) itemView.findViewById(R.id.cardpart);
            experience = (TextView) itemView.findViewById(R.id.cardExper);
            hours = (TextView) itemView.findViewById(R.id.cardHours);
            date = (TextView) itemView.findViewById(R.id.cardDate);
            address = (TextView) itemView.findViewById(R.id.cardAddress);
            contact = (TextView) itemView.findViewById(R.id.cardContact);


        }
    }
}
