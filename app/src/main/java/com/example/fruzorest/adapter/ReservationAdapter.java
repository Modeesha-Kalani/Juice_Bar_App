package com.example.fruzorest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruzorest.R;
import com.example.fruzorest.ReservationManageActivity;
import com.example.fruzorest.model.Order;
import com.example.fruzorest.model.Reservation;
import com.example.fruzorest.util.GFG;
import com.example.fruzorest.viewholder.OrderViewHolder;
import com.example.fruzorest.viewholder.ReservationViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    //create other methods except onclick

    ArrayList<Reservation> list;
    Context context;

    //get data to an arraylist
    public ReservationAdapter(ArrayList<Reservation> rlist) {
        this.list = rlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //get context object
        context = parent.getContext();
        //get layout file
        //get that returned file
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservationrecycler, parent, false);

        inflate.setOnClickListener(this);

        //pass view to the viewHolder class
        return new ReservationViewHolder(inflate);
    }

    //set data into ViewHolder
    @Override                                                               //loop
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReservationViewHolder viewHolder = (ReservationViewHolder) holder;
        //pass position
        Reservation reservation = list.get(position);
        viewHolder.setTag(position);
        viewHolder.setDate(reservation.getDate());
        viewHolder.setTime("You have a Reservation From " + reservation.getTime() + " to " + reservation.getDuration());
        viewHolder.setDuration("("+GFG.findDifference(reservation.getTime(), reservation.getDuration())+"Hours )");
        //duration=leaving time
    }

    @Override
    //get number of positions/reservations
    public int getItemCount() {
        return list.size();
    }

    //when one item is clicked from all
    @Override
    public void onClick(View v) {
        //get linear layout inside main linear layout
        LinearLayout lay = v.findViewById(R.id.re_root);
        int tag = (int) lay.getTag();
        Intent i = new Intent(context, ReservationManageActivity.class);
        //get arraylist's one by one
        i.putExtra("reservid",list.get(tag).getId());
        context.startActivity(i);
        //pass activity as context because this is an adapter

    }
}

