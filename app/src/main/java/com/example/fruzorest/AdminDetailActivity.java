package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fruzorest.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDetailActivity extends AppCompatActivity {
    private String adminid;
    private TextView fullname1, fullname2, role, nic, email, phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);
        fullname1 = findViewById(R.id.av_fullname1);
        fullname2 = findViewById(R.id.av_fullname2);
        role = findViewById(R.id.av_adminrole);
        nic = findViewById(R.id.av_nic);
        email = findViewById(R.id.av_email);
        phone = findViewById(R.id.av_phone);

        Intent intent = getIntent();
        adminid = intent.getStringExtra("admin");
        loadDetails(adminid);
    }

    private void loadDetails(String admin) {
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        DatabaseReference child = instance.getReference("user").child("allusers").child(admin);
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User value = snapshot.getValue(User.class);
                fullname1.setText(value.getFirstname() + " " + value.getLastname());
                fullname2.setText(value.getFirstname() + " " + value.getLastname());
                role.setText(value.getRole());
                nic.setText(value.getNic());
                email.setText(value.getEmail());
                phone.setText(value.getMobile());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToEditInfo(View view) {
        Intent i = new Intent(getApplicationContext(), EditAdminActivity.class);
        Bundle b = new Bundle();
        i.putExtra("admin", adminid);
        startActivity(i);
    }
}