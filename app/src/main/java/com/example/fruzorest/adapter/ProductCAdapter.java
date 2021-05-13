package com.example.fruzorest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruzorest.ProductViewCustomerActivity;
import com.example.fruzorest.R;
import com.example.fruzorest.model.ProductInfo;
import com.example.fruzorest.viewholder.ProductViewHolder;


import java.util.ArrayList;

public class ProductCAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    ArrayList<ProductInfo> list;
    Context context;

    public ProductCAdapter(ArrayList<ProductInfo> plist) {
        this.list = plist;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, ProductViewCustomerActivity.class);
        i.putExtra("pid",list.get((int) v.findViewById(R.id.pre_linear).getTag()).getProduct().getId());
        i.putExtra("ptype",list.get((int) v.findViewById(R.id.pre_linear).getTag()).getProduct().getType());
        context.startActivity(i);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.productrecycler, parent, false);
        inflate.setOnClickListener(this);
        return new ProductViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder viewholder = (ProductViewHolder) holder;
        viewholder.setContext(context);
        viewholder.setImage(list.get(position).getUri());//number of the image
        viewholder.setName(list.get(position).getProduct().getName());
        viewholder.setPrice(list.get(position).getProduct().getReg_price()+"");
        viewholder.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    } //array size
}
