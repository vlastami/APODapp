package com.example.apodapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.content.Intent;

import com.example.apodapp.data.ApiClient;
import com.example.apodapp.data.ApodImage;
import com.example.apodapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApodAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ApodAdapter apodAdapter;
    private SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        apodAdapter = new ApodAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(apodAdapter);

        ApiClient.loadImagesForLastWeek(this, apodAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                apodAdapter.filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onItemClick(ApodImage image) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("date", image.getDate());
        intent.putExtra("title", image.getTitle());
        intent.putExtra("explanation", image.getExplanation());
        intent.putExtra("url", image.getUrl());
        startActivity(intent);
    }
}
