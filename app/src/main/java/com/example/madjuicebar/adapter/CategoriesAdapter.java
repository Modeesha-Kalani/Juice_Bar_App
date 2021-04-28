package com.example.madjuicebar.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madjuicebar.R;
import com.example.madjuicebar.model.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    Context context;
    List<Categories> categoriesList;

    public CategoriesAdapter(Context context, List<Categories> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    public static final class CategoriesViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.categories_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        holder.itemImage.setImageResource(categoriesList.get(position).getImage());
        holder.itemName.setText(categoriesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}
