package com.example.fruzorest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.fruzorest.adapter.ProductCAdapter;
import com.example.fruzorest.model.Product;
import com.example.fruzorest.model.ProductInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductCViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_c_view);
        recyclerView = findViewById(R.id.pc_recycler);
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        loadProducts(type);
    }

    private void loadProducts(String type) {
        System.out.println(type);
        ArrayList<ProductInfo> infolist = new ArrayList<>();
        DatabaseReference product = FirebaseDatabase.getInstance().getReference("products").child(type);
        StorageReference child1 = FirebaseStorage.getInstance().getReference("products");
        product.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot snap : children) {
                    Product value = snap.getValue(Product.class);
                    System.out.println(value.getName());
                    ProductInfo info = new ProductInfo();
                    info.setProduct(value);
                    //load Image
                    StorageReference child2 = child1.child(value.getId());
                    child2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            info.setUri(uri);
                            ProductCAdapter adapter = new ProductCAdapter(infolist);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                    infolist.add(info);
                }
                ProductCAdapter adapter = new ProductCAdapter(infolist);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}