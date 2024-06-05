package com.example.apodapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.content.Intent;

import com.example.apodapp.service.ApodImageService;
import com.example.apodapp.data.ApodImage;
import com.example.apodapp.R;

import java.util.ArrayList;

/**
 * Třída MainActivity je hlavní aktivita aplikace, která zobrazuje seznam obrázků NASA APOD (Astronomy Picture of the Day).
 */
public class MainActivity extends AppCompatActivity implements ApodAdapter.OnItemClickListener {
    private RecyclerView recyclerView;  // RecyclerView pro zobrazení seznamu obrázků
    private ApodAdapter apodAdapter;  // Adaptér pro RecyclerView
    private SearchView searchView;  // SearchView pro filtrování obrázků
    private final ApodImageService apodImageService = new ApodImageService(this);  // Servisní vrstva pro načítání obrázků

    /**
     * Metoda onCreate je volána při vytváření aktivity. Inicializuje UI komponenty a načítá data.
     * @param savedInstanceState Stav instance uložený v případě přerušení a obnovy aktivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Astronomical Picture of the Day App");

        // Inicializace RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        apodAdapter = new ApodAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(apodAdapter);

        // Načtení obrázků za poslední týden
        apodImageService.loadImagesForLastWeek(apodAdapter);

        // Inicializace SearchView pro filtrování obrázků
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

    /**
     * Metoda onItemClick je volána při kliknutí na položku v seznamu obrázků.
     * @param image Obrázek APOD, na který bylo kliknuto.
     */
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
