package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fruzorest.adapter.OrderCAdapter;
import com.example.fruzorest.adapter.ProductCAdapter;
import com.example.fruzorest.model.Order;
import com.example.fruzorest.model.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMyOrderActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager; //create an object from layout manager inner class


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_order);
        recycler = findViewById(R.id.myorderrecycler);
        manager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) manager).setReverseLayout(true);
        ((LinearLayoutManager) manager).setStackFromEnd(true);
        recycler.setLayoutManager(manager);
        recycler.setHasFixedSize(true);


        loadMyOrders();
    }

    private void loadMyOrders() {
        ArrayList<Order> olist = new ArrayList<>();
        DatabaseReference child = FirebaseDatabase.getInstance().getReference("order").child("userorder").child(Util.currentuser.getUsername());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot snapshot1 : children) {
                    Order value = snapshot1.getValue(Order.class);
                    olist.add(value);
                }
                OrderCAdapter adapter = new OrderCAdapter(olist);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() { // to refresh the page
        super.onResume();
        loadMyOrders();
    }
}
