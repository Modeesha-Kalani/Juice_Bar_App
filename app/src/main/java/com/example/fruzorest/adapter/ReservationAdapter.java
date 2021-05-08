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
    ArrayList<Reservation> list;
    Context context;
    SimpleDateFormat format;

    public ReservationAdapter(ArrayList<Reservation> rlist) {
        this.list = rlist;
        this.format = new SimpleDateFormat("yyy-MM-dd");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservationrecycler, parent, false);
        inflate.setOnClickListener(this);
        return new ReservationViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReservationViewHolder viewHolder = (ReservationViewHolder) holder;
        Reservation reservation = list.get(position);
        viewHolder.setTag(position);
        viewHolder.setDate(reservation.getDate());
        viewHolder.setTime("you have Reservation From " + reservation.getTime() + " to " + reservation.getDuration());
        viewHolder.setDuration("("+GFG.findDifference(reservation.getTime(), reservation.getDuration())+"Hours )");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        LinearLayout lay = v.findViewById(R.id.re_root);
        int tag = (int) lay.getTag();
        Intent i = new Intent(context, ReservationManageActivity.class);
        i.putExtra("reservid",list.get(tag).getId());
        context.startActivity(i);
        
    }
}
