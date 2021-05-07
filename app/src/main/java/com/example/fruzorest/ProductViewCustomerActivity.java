package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fruzorest.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductViewCustomerActivity extends AppCompatActivity {
    private String productid, ptype;
    private ImageView imageView;
    private TextView name, regprice, largeprice, ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view_customer);
        Intent intent = getIntent();
        productid = intent.getStringExtra("pid");
        ptype = intent.getStringExtra("ptype");
        imageView = findViewById(R.id.pv_imgview);
        name = findViewById(R.id.pv_name);
        regprice = findViewById(R.id.pv_regprice);
        largeprice = findViewById(R.id.pv_largeprice);
        ingredients = findViewById(R.id.pv_ingredients);

        loadProductDetails(productid, ptype);


    }

    private void loadProductDetails(String productid, String ptype) {

        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products").child(ptype).child(productid);
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product value = snapshot.getValue(Product.class);
                name.setText(value.getName());
                regprice.setText(value.getReg_price() + "");
                largeprice.setText(value.getLarge_price() + "");
                ingredients.setText(value.getIngredients());
                StorageReference products1 = FirebaseStorage.getInstance().getReference("products").child(value.getId());
                products1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(imageView);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    
}