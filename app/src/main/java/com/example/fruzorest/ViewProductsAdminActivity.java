package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fruzorest.model.Product;
import com.example.fruzorest.model.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewProductsAdminActivity extends AppCompatActivity {
    private String productid, ptype;
    private ImageView imageView;
    private TextView name, regprice, largeprice, ingredients;
    private Product temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products_admin);
        Intent intent = getIntent();
        productid = intent.getStringExtra("pid");
        ptype = intent.getStringExtra("ptype");
        imageView = findViewById(R.id.pva_image);
        name = findViewById(R.id.pva_name);
        regprice = findViewById(R.id.pva_regularprice);
        largeprice = findViewById(R.id.pva_largeprice);
        ingredients = findViewById(R.id.pva_ingredients);

        loadProductDetails(productid, ptype);
    }

    public void updateProduct(View view) {
        Intent i = new Intent(this, ProductUpdateActivity.class);
        i.putExtra("pid", productid);
        i.putExtra("ptype", ptype);
        startActivity(i);


    }

    public void deleteProduct(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProductsAdminActivity.this);
        builder.setMessage("Do You want to Delete This Product? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DatabaseReference child = FirebaseDatabase.getInstance()
                                .getReference("products")
                                .child(ptype)
                                .child(productid);
                        Task<Void> voidTask = child.removeValue();
                        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Product Deleted !",
                                        Toast.LENGTH_LONG).show();
                                StorageReference products = FirebaseStorage.getInstance().getReference("products").child(temp.getId());
                                products.delete();
                                DatabaseReference userorder = FirebaseDatabase.getInstance().getReference("products")
                                        .child(ptype)
                                        .child("fav")
                                        .child(productid);

                                Task<Void> voidTask2 = userorder.removeValue();
                                voidTask2.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Product Deleted !",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }

                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Delete Product");
        alert.show();



    }

    private void loadProductDetails(String productid, String ptype) {

        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products").child(ptype).child(productid);
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product value = snapshot.getValue(Product.class);
                temp = value;
                name.setText(value.getName());
                regprice.setText("Rs." + Util.formatDecimal(value.getReg_price()) + "");
                largeprice.setText("Rs." + Util.formatDecimal(value.getLarge_price()) + "");
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
    @Override
    protected void onResume() {
        super.onResume();
        loadProductDetails(productid, ptype);
    }

    public void addToFav(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProductsAdminActivity.this);
        builder.setMessage("Do You want to Add This Product to Favourit List ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference products = FirebaseDatabase.getInstance().
                                getReference("products").
                                child("fav").child(temp.getId());
                        Task<Void> voidTask = products.setValue(temp);
                        voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Product added most popular list !",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Add To Favourits");
        alert.show();


    }


}