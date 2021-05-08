package com.example.fruzorest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fruzorest.model.Reservation;
import com.example.fruzorest.util.GFG;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ReservationViewFragment extends Fragment {
    private Bundle bundle;
    private TextView table, timeof, duration;

    public ReservationViewFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = getArguments();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        table = getView().findViewById(R.id.rf_table);
        timeof = getView().findViewById(R.id.rf_time);
        duration = getView().findViewById(R.id.rf_duration);
        loadValues();
    }

    private void loadValues() {
        String reservid = bundle.getString("reservid");
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("reservation").child("all").child(reservid);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reservation value = snapshot.getValue(Reservation.class);
                table.setText("Table "+value.getTableid());
                timeof.setText(value.getTime());
                duration.setText("For "+GFG.findDifference(value.getTime(),value.getDuration())+" Hours");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
