package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fruzorest.model.User;
import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditAdminActivity extends AppCompatActivity {
    private EditText fname, lname, nic, email, phone, password, role;
    private TextView uname;
    private ImageView profilepick;

    private User user;
    private InputStream tempuri;

    private boolean imgselect;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);
        fname = findViewById(R.id.ed_fname);
        lname = findViewById(R.id.ed_lname);
        nic = findViewById(R.id.ed_nic);
        email = findViewById(R.id.ed_email);
        phone = findViewById(R.id.ed_phone);
        password = findViewById(R.id.ed_password);
        uname = findViewById(R.id.ed_username);
        profilepick = findViewById(R.id.ed_imgeview);
        role = findViewById(R.id.ed_role);

        Intent i = getIntent();
        String admin = i.getStringExtra("admin");
        loadDetails(admin);
        imgselect = false;


    }

    private void loadDetails(String admin) {
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference alluser = userref.child("allusers").child(admin);
        alluser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User value = snapshot.getValue(User.class);
                user = value;
                fname.setText(value.getFirstname());
                lname.setText(value.getLastname());
                nic.setText(value.getNic());
                email.setText(value.getEmail());
                phone.setText(value.getMobile());
                password.setText(value.getPassword());
                uname.setText(value.getUsername());
                role.setText(value.getRole());


                StorageReference child = FirebaseStorage.getInstance()
                        .getReference("user")
                        .child("profilepic")
                        .child(value.getUsername());
                Task<Uri> downloadUrl = child.getDownloadUrl();
                downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        profilepick.setImageURI(null);
                        Glide.with(getApplicationContext())
                                .load(uri).apply(RequestOptions.circleCropTransform()).into(profilepick);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void updateInfo(View view) {
        DatabaseReference child = FirebaseDatabase.getInstance()
                .getReference("user")
                .child("allusers")
                .child(user.getUsername());
        user.setFirstname(fname.getText().toString());
        user.setLastname(lname.getText().toString());
        user.setNic(nic.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setMobile(phone.getText().toString());
        user.setRole(role.getText().toString());

        Task<Void> voidTask = child.setValue(user);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (imgselect) {
                    FirebaseStorage instance = FirebaseStorage.getInstance();
                    StorageReference child = instance.getReference("user").child("profilepic").child(user.getUsername());
                    child.putStream(tempuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            hideProgressDialog();
                            Snackbar snackbar = Snackbar
                                    .make(getCurrentFocus(), "User  Account Update Success !", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });


                } else {
                    hideProgressDialog();
                    Snackbar snackbar = Snackbar
                            .make(getCurrentFocus(), "User Account Update Success !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
    }

    public void selectImage(View view) {

        PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                System.out.println("image selected");
                profilepick.setImageURI(null);
                profilepick.setImageURI(r.getUri());
                imgselect = true;
                try {
                    tempuri = getApplicationContext().getContentResolver().openInputStream(r.getUri());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }).show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 135) {
            Toast.makeText(getApplicationContext(), "Image Selected", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            profilepick.setImageURI(uri);
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(EditAdminActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
}