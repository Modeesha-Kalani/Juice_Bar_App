
package com.example.madjuicebar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.AttributeSet;

import com.example.madjuicebar.adapter.CategoriesAdapter;
import com.example.madjuicebar.adapter.MostpopularAdapter;
import com.example.madjuicebar.model.Categories;
import com.example.madjuicebar.model.MostPopular;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoriesRecycler, mostPopularRecycler;
    CategoriesAdapter categoriesAdapter;
    MostpopularAdapter mostpopularAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Categories> categoriesList = new ArrayList<>();
        categoriesList.add(new Categories("Juice", R.drawable.juice));
        categoriesList.add(new Categories("Salad", R.drawable.salad));
        categoriesList.add(new Categories("Smoothie", R.drawable.smoothie));

        setCategoriesRecycler(categoriesList);



        List<MostPopular> mostPopularList = new ArrayList<>();
        mostPopularList.add(new MostPopular("Avocado Juice", "RS","200",R.drawable.avocado));
        mostPopularList.add(new MostPopular("Strawberry Juice", "RS","400",R.drawable.strawberryyyyy));
        mostPopularList.add(new MostPopular("Mango Milkshake", "RS","200",R.drawable.mangomilkshake));
        mostPopularList.add(new MostPopular("Orange Juice", "RS","200",R.drawable.orange));
        mostPopularList.add(new MostPopular("Lime Juice", "RS","200",R.drawable.lime_juice));


    }
    private void setCategoriesRecycler(List<Categories> categoriesList){

        categoriesRecycler = findViewById(R.id.categories_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoriesRecycler.setLayoutManager(layoutManager);
        categoriesAdapter = new CategoriesAdapter(this, categoriesList);
        categoriesRecycler.setAdapter(categoriesAdapter);
    }

    private void setMostPopularRecycler(List<MostPopular> mostPopularList){

        mostPopularRecycler = findViewById(R.id.mostpopular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mostPopularRecycler.setLayoutManager(layoutManager);
        mostpopularAdapter = new MostpopularAdapter(this, mostPopularList);
        mostPopularRecycler.setAdapter(mostpopularAdapter);
    }


    }
