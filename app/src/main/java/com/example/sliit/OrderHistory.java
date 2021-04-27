package com.example.sliit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class OrderHistory extends AppCompatActivity {

    String h1[];
    int images[] = {R.drawable.strawberry,R.drawable.avacado,R.drawable.orange,R.drawable.lime};
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerView);

       h1 = getResources().getStringArray(R.array.history);

        myRecyclerAdapter  MyRecyclerAdapter = new myRecyclerAdapter(this, h1, images);
        recyclerView.setAdapter(MyRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}