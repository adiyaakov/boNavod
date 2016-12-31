package com.marvelapp.myapplication.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marvelapp.myapplication.R;

public class ViewCatagoryHolder extends RecyclerView.ViewHolder{

   CardView cardView;
   TextView category,part,experience,hours,date,address,contact;
   Button trash_btn;
   public ViewCatagoryHolder(View itemView) {
       super(itemView);
       cardView = (CardView)itemView.findViewById(R.id.card);
       trash_btn = (Button)itemView.findViewById(R.id.trash_btn);
       category = (TextView)itemView.findViewById(R.id.cardCategory);
       part = (TextView)itemView.findViewById(R.id.cardpart);
       experience = (TextView)itemView.findViewById(R.id.cardExper);
       hours = (TextView)itemView.findViewById(R.id.cardHours);
       date = (TextView)itemView.findViewById(R.id.cardDate);
       address = (TextView)itemView.findViewById(R.id.cardAddress);
       contact = (TextView)itemView.findViewById(R.id.cardContact);


   }


}
