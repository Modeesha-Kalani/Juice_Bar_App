package com.example.fruzorest.viewholder;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruzorest.R;

public class ReservationViewHolder extends RecyclerView.ViewHolder {
    TextView date, time, duration;
    LinearLayout root;

    public ReservationViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.re_date);
        time = itemView.findViewById(R.id.re_time);
        duration = itemView.findViewById(R.id.re_duration);
        root = itemView.findViewById(R.id.re_root);

    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setTime(String timeof) {
        this.time.setText(timeof);
    }

    public void setDuration(String duration) {
        this.duration.setText(duration);
    }

    public void setTag(int i) {
        root.setTag(i);
    }

    public int getTag() {
        return (int) root.getTag();
    }


}
