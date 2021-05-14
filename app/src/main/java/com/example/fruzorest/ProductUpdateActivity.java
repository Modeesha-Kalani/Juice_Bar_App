package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.example.fruzorest.model.Product;
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

public class ProductUpdateActivity extends AppCompatActivity {
    private ImageView imgview;
    private EditText pname, regprice, largeprice, ingredients;
    private InputStream tempuri;

    private String productid;
    private String ptype;

    private Product tempproduct;

    private boolean selectimg;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);
        Intent intent = getIntent();
        productid = intent.getStringExtra("pid");
        ptype = intent.getStringExtra("ptype");

        imgview = findViewById(R.id.up_img);
        pname = findViewById(R.id.up_pname);
        regprice = findViewById(R.id.up_regprice);
        largeprice = findViewById(R.id.up_largeprice);
        ingredients = findViewById(R.id.up_ingredients);

        selectimg = false;

        loadProductDetails(productid, ptype);

    }

    private void loadProductDetails(String productid, String ptype) {


        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products").child(ptype).child(productid);
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product value = snapshot.getValue(Product.class);
                tempproduct = value;
                pname.setText(value.getName());
                regprice.setText(value.getReg_price() + "");
                largeprice.setText(value.getLarge_price() + "");
                ingredients.setText(value.getIngredients());
                StorageReference products1 = FirebaseStorage.getInstance().getReference("products").child(value.getId());
                products1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(imgview);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateProduct(View view) {

        showProgressDialog();
        Product p = tempproduct;
        p.setName(pname.getText().toString());
        p.setIngredients(ingredients.getText().toString());
        p.setLarge_price(Double.parseDouble(largeprice.getText().toString()));
        p.setReg_price(Double.parseDouble(regprice.getText().toString()));
        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products").child(p.getType()).child(p.getId());
        Task<Void> voidTask = products.setValue(p);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (selectimg) {
                    StorageReference products1 = FirebaseStorage.getInstance().getReference("products").child(p.getId());
                    products1.putStream(tempuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            hideProgressDialog();
                            Snackbar snackbar = Snackbar
                                    .make(getCurrentFocus(), "Product Update Success !", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                } else {
                    hideProgressDialog();
                    Snackbar snackbar = Snackbar
                            .make(getCurrentFocus(), "Product Update Success !", Snackbar.LENGTH_LONG);
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
                imgview.setImageURI(null);
                imgview.setImageURI(r.getUri());
                selectimg = true;

                try {
                    tempuri = getApplicationContext().getContentResolver().openInputStream(r.getUri());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }).show(getSupportFragmentManager());
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ProductUpdateActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

}