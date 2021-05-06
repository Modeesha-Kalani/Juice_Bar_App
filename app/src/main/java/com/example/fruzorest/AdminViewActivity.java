package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.example.fruzorest.adapter.ViewAllAdminAdapter;
import com.example.fruzorest.model.User;
import com.example.fruzorest.model.UserInfo;
import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminViewActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        recycler = this.findViewById(R.id.adminrecycler);
        manager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(manager);
        loadAdmins();
    }


    private void loadAdmins() {
        ArrayList<UserInfo> infolist = new ArrayList<>();
        // if (Util.isOnline(getApplicationContext())) {
        if (true) {
            System.out.println("cad");
            FirebaseDatabase instance = FirebaseDatabase.getInstance();
            DatabaseReference child = instance.getReference("user").child("allusers");
            StorageReference child1 = FirebaseStorage.getInstance().getReference("user").child("profilepic");
            child.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    for (DataSnapshot snap : children) {
                        User value = snap.getValue(User.class);
                        UserInfo info = new UserInfo();
                        info.setUser(value);
                        info.setContext(getApplicationContext());
                        //load Image
                        StorageReference child2 = child1.child(value.getUsername());
                        child2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                info.setUri(uri);
                                ViewAllAdminAdapter adapter = new ViewAllAdminAdapter(infolist);
                                recycler.setAdapter(adapter);
                            }
                        });
                        infolist.add(info);
                    }
                    ViewAllAdminAdapter adapter = new ViewAllAdminAdapter(infolist);
                    recycler.setAdapter(adapter);
                    recycler.setHasFixedSize(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }
}