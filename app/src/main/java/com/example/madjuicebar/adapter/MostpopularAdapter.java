package com.example.madjuicebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madjuicebar.R;
import com.example.madjuicebar.model.MostPopular;

import java.util.List;

public class MostpopularAdapter extends RecyclerView.Adapter<MostpopularAdapter.MostPopularViewHolder> {

    Context context;
    List<MostPopular> mostpopularList;

    public MostpopularAdapter(Context context, List<MostPopular> categoriesList) {
        this.context = context;
        this.mostpopularList = mostpopularList;
    }

    public static final class MostPopularViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName, itemRs, itemPrice;

        public MostPopularViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_imagepopolar);
            itemName = itemView.findViewById(R.id.itemnamepopular);
            itemPrice = itemView.findViewById(R.id.price);
            itemRs = itemView.findViewById(R.id.rsText);
        }
    }
    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MostPopularViewHolder(LayoutInflater.from(context).inflate(R.layout.mostpopular_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MostPopularViewHolder holder, int position) {

        holder.itemImage.setImageResource(mostpopularList.get(position).getImage());
        holder.itemName.setText(mostpopularList.get(position).getName());
        holder.itemPrice.setText(mostpopularList.get(position).getPrice());
        holder.itemRs.setText(mostpopularList.get(position).getRs());
    }

    @Override
    public int getItemCount() {

        return mostpopularList.size();
    }
}
