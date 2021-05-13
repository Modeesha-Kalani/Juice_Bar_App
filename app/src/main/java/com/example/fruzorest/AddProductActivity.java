package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.fruzorest.model.Product;
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

public class AddProductActivity extends AppCompatActivity {
    private ImageView imgview;
    private EditText pname, regprice, largeprice, ingredients;
    private InputStream tempuri;
    private RadioGroup radiogroup;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        imgview = findViewById(R.id.ap_img);
        pname = findViewById(R.id.ap_pname);
        regprice = findViewById(R.id.ap_regprice);
        largeprice = findViewById(R.id.ap_largeprice);
        ingredients = findViewById(R.id.ap_ingredients);
        radiogroup = findViewById(R.id.ap_radiogroup);

    }

    public void selectImage(View view) {
        PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                System.out.println("image selected");
                imgview.setImageURI(null);
                imgview.setImageURI(r.getUri());

                try {
                    tempuri = getApplicationContext().getContentResolver().openInputStream(r.getUri());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }).show(getSupportFragmentManager());

    }


    public void addProduct(View view) {
        showProgressDialog();
        int checkedRadioButtonId = radiogroup.getCheckedRadioButtonId();
        String category = "";
        if(checkedRadioButtonId == R.id.ap_rbsalad){
            category="salad";
        }else if(checkedRadioButtonId == R.id.ap_rbsmoothi){
            category="smoothi";
        }else{
            category="juice";
        }

        Product p = new Product();
        p.setName(pname.getText().toString());
        p.setIngredients(ingredients.getText().toString());
        p.setLarge_price(Double.parseDouble(largeprice.getText().toString()));
        p.setReg_price(Double.parseDouble(regprice.getText().toString()));
        p.setType(category);

        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products").child(category);
        DatabaseReference push = products.push();
        p.setId(push.getKey());
        Task<Void> voidTask = push.setValue(p);
        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                StorageReference products1 = FirebaseStorage.getInstance().getReference("products").child(p.getId());
                products1.putStream(tempuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        hideProgressDialog();
                        Snackbar snackbar = Snackbar
                                .make(getCurrentFocus(), "New  Product Added Success !", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });


    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(AddProductActivity.this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
}