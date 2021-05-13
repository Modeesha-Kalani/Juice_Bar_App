package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fruzorest.adapter.OrderCAdapter;
import com.example.fruzorest.adapter.ReservationAdapter;
import com.example.fruzorest.model.Order;
import com.example.fruzorest.model.Reservation;
import com.example.fruzorest.model.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewReservationActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);
        recycler = findViewById(R.id.reserv_recycler);
        manager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(manager);
        recycler.setHasFixedSize(true);

        loadReservation();
    }

    private void loadReservation() {
        ArrayList<Reservation> olist = new ArrayList<>();
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("reservation").child("user").child(Util.currentuser.getUsername());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot snapshot1 : children) {
                    Reservation value = snapshot1.getValue(Reservation.class);
                    olist.add(value);
                }
                ReservationAdapter adapter = new ReservationAdapter(olist);
                recycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//myyyyyyyy
    @Override
    protected void onResume() {
        super.onResume();
        loadReservation();
    }
}