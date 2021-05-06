package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fruzorest.model.User;
import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddAdminActivity extends AppCompatActivity {

    private EditText fname;
    private EditText lname;
    private EditText nic;
    private EditText email;
    private EditText phone;
    private EditText adminrole;
    private EditText username;
    private EditText password;
    private EditText repassword;
    private ImageView imageView;

    private InputStream tempuri;

    //Firebase
    FirebaseDatabase firebase;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        fname = findViewById(R.id.aa_fname);
        lname = findViewById(R.id.aa_lname);
        nic = findViewById(R.id.aa_nic);
        email = findViewById(R.id.aa_email);
        phone = findViewById(R.id.aa_phone);
        adminrole = findViewById(R.id.aa_role);
        username = findViewById(R.id.aa_username);
        password = findViewById(R.id.aa_password);
        repassword = findViewById(R.id.aa_repassword);
        imageView = findViewById(R.id.aa_imageview);
        firebase = FirebaseDatabase.getInstance();


    }

    public void addAdmin(View view) {
        showProgressDialog();
        if (email.getText().toString().isEmpty() ||
                phone.getText().toString().isEmpty() ||
                username.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                repassword.getText().toString().isEmpty() ||
                nic.getText().toString().isEmpty() ||
                adminrole.getText().toString().isEmpty()||
                fname.getText().toString().isEmpty()||
                lname.getText().toString().isEmpty()) {
//check values
            hideProgressDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(AddAdminActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
            builder.setTitle("Cannot Create User Account");
            builder.setMessage("Fill All textfields").setCancelable(false);
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    hideProgressDialog();
                }
            }).setIcon(R.drawable.ic_baseline_error_outline_24);
            builder.create().show();

        } else {
            if (password.getText().toString().equals(repassword.getText().toString())) {
                User user = new User();
                user.setEmail(email.getText().toString());
                user.setMobile(phone.getText().toString());
                user.setUsername(username.getText().toString());
                user.setFirstname(fname.getText().toString());
                user.setLastname(lname.getText().toString());
                user.setNic(nic.getText().toString());
                user.setPassword(password.getText().toString());
                user.setStatus(1);
                user.setRole(adminrole.getText().toString());
                user.setUserlevel(2);
                DatabaseReference userref = firebase.getReference("user");
                DatabaseReference allusers = userref.child("allusers").child(user.getUsername());
                DatabaseReference admins = userref.child("admin").child(user.getUsername());

                Task<Void> push = allusers.setValue(user);
                push.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Task<Void> voidTask = admins.setValue(user);
                        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseStorage instance = FirebaseStorage.getInstance();
                                StorageReference child = instance.getReference("user").child("profilepic").child(user.getUsername());
                                child.putStream(tempuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        hideProgressDialog();
                                        Snackbar snackbar = Snackbar
                                                .make(getCurrentFocus(), "New  Account Created Success !", Snackbar.LENGTH_LONG);
                                        snackbar.show();

                                    }
                                });

                                hideProgressDialog();
                                Snackbar snackbar = Snackbar
                                        .make(getCurrentFocus(), "New User Account Created Success !", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            }
                        });
                    }


                });
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAdminActivity.this, R.style.Theme_AppCompat_Dialog_MinWidth);
                builder.setTitle("Cannot Create User Account");
                builder.setMessage("Password doesn't match").setCancelable(false);
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        hideProgressDialog();
                    }
                }).setIcon(R.drawable.ic_baseline_error_outline_24);
                builder.create().show();

            }
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AddAdminActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void selectImage(View view) {
        PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                System.out.println("image selected");
                imageView.setImageURI(null);
                imageView.setImageURI(r.getUri());

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
            imageView.setImageURI(uri);
        }
    }
}