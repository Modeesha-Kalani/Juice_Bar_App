package com.example.fruzorest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruzorest.CustomerOrderViewActivity;
import com.example.fruzorest.R;
import com.example.fruzorest.model.Order;
import com.example.fruzorest.viewholder.OrderViewHolder;
import com.example.fruzorest.viewholder.ProductViewHolder;

import java.util.ArrayList;

public class OrderCAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    ArrayList<Order>  list; // to store order objects
    Context context;

    //constructor
    public OrderCAdapter(ArrayList<Order> list) {
        this.list = list; //get data to an array list
    }

    //override abstract classes
    @Override
    public void onClick(View v) {
        LinearLayout root = v.findViewById(R.id.orderlinearlayout);
        Order order = list.get((int) root.getTag());
        Intent i = new Intent(context, CustomerOrderViewActivity.class);
        i.putExtra("orderid",order.getId());
        context.startActivity(i);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //get context object
        context = parent.getContext();
        //get layout file with the returned file
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrecycler, parent, false);
        inflate.setOnClickListener(this);

        //pass view to the viewHolder class
        return new OrderViewHolder(inflate);
    }

    @Override
    //set the data to viewHolder
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder viewHolder = (OrderViewHolder) holder;
        viewHolder.setContext(context);

        //pass position
        Order order = list.get(position);
        viewHolder.setTag(position);
        viewHolder.setTotal(order.getTotal()+"");
        viewHolder.setRegdetail("Rs."+order.getRprice()+ " X " +order.getRqty());
        viewHolder.setLargdetail("Rs."+order.getLprice()+ " X " +order.getLqty());
        viewHolder.setpName(order.getPname());

    }

    @Override
    public int getItemCount() {
        return list.size(); // get the array size
    }
}
