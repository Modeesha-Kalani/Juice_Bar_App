package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fruzorest.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminDetailActivity extends AppCompatActivity {
    private String adminid;
    private TextView fullname1, fullname2, role, nic, email, phone;
    private ImageView imageview;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

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
        imageview = findViewById(R.id.imageView4);

        Intent intent = getIntent();
        adminid = intent.getStringExtra("admin");
        loadDetails(adminid);
    }

    private void loadDetails(String admin) {
        showProgressDialog();
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
                StorageReference child = FirebaseStorage.getInstance()
                        .getReference("user")
                        .child("profilepic")
                        .child(value.getUsername());
                Task<Uri> downloadUrl = child.getDownloadUrl();
                downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageview.setImageURI(null);
                        Glide.with(getApplicationContext())
                                .load(uri).apply(RequestOptions.circleCropTransform()).into(imageview);
                        hideProgressDialog();
                    }
                });

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

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AdminDetailActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
}