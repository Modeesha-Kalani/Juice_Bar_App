package com.example.fruzorest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fruzorest.model.Reservation;
import com.example.fruzorest.model.Util;
import com.example.fruzorest.util.GFG;
import com.example.fruzorest.util.TimePickerDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class UpdateReservationFragment extends Fragment {
    private Bundle bundle;
    private EditText reservfor,contactfor, timeof, duration;
    private Button updatereservationbtn;
    private Reservation reservation;


    public UpdateReservationFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_update_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reservfor = getView().findViewById(R.id.ur_reservfor);
        contactfor = getView().findViewById(R.id.ur_contactfor);
        timeof = getView().findViewById(R.id.ur_timetext);
        duration = getView().findViewById(R.id.ur_durationtext);
        updatereservationbtn = getView().findViewById(R.id.button022);

        updatereservationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeReservation();
            }
        });

        timeof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepicker = new TimePickerDialog(timeof);
                timepicker.show(getFragmentManager(), "timepicker");
            }
        });

        duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepicker = new TimePickerDialog(duration);
                timepicker.show(getFragmentManager(), "timepicker");
            }
        });

        loadValues();
    }
    private void loadValues() {
        String reservid = bundle.getString("reservid");
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("reservation").child("all").child(reservid);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reservation value = snapshot.getValue(Reservation.class);
                reservation = value;
                reservfor.setText(value.getReservfor());
                contactfor.setText(value.getContact());
                timeof.setText(value.getTime());
                duration.setText(value.getDuration());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void makeReservation() {

        reservation.setTime(timeof.getText().toString());
        reservation.setDuration(duration.getText().toString());
        reservation.setContact(contactfor.getText().toString());
        reservation.setReservfor(reservfor.getText().toString());


        DatabaseReference child = FirebaseDatabase.getInstance().getReference("reservation");
        DatabaseReference all = child.child("all").child(reservation.getId());
        Task<Void> voidTask = all.setValue(reservation);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference child1 = child.child("user").child(reservation.getUserid()).child(reservation.getId());
                child1.setValue(reservation).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackbar = Snackbar
                                .make(getView(), " Reservation Updated Success !", Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }
                });
            }
        });


    }

}