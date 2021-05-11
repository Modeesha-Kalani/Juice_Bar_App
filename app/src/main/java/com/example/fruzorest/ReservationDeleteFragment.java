package com.example.fruzorest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fruzorest.model.Reservation;
import com.example.fruzorest.util.GFG;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReservationDeleteFragment extends Fragment {

    private Bundle bundle;
    private TextView table, timeof, duration;
    private Reservation reservation;
    private Button deletebtn;

    public ReservationDeleteFragment() {
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
        return inflater.inflate(R.layout.fragment_reservation_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        table = getView().findViewById(R.id.rdf_table);
        timeof = getView().findViewById(R.id.rdf_time);
        duration = getView().findViewById(R.id.rdf_duration);
        deletebtn = getView().findViewById(R.id.button26);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
deleteReservation();
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
                table.setText("Table "+value.getTableid());
                timeof.setText(value.getTime());
                duration.setText("For "+ GFG.findDifference(value.getTime(),value.getDuration())+" Hours");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteReservation() {
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("reservation");
        DatabaseReference all = child.child("all").child(reservation.getId());
        Task<Void> voidTask = all.removeValue();
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference child1 = child.child("user").child(reservation.getUserid()).child(reservation.getId());
                child1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackbar = Snackbar
                                .make(getView(), " Reservation Canceled Success !", Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }
                });
            }
        });


    }
}