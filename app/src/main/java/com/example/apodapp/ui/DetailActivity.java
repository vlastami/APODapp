package com.example.apodapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.apodapp.R;

/**
 * Třída DetailActivity zobrazuje podrobnosti o vybraném obrázku NASA APOD.
 */
public class DetailActivity extends AppCompatActivity {

    /**
     * Metoda onCreate je volána při vytváření aktivity. Inicializuje UI komponenty a nastavuje data přijatá z intentu.
     * @param savedInstanceState Stav instance uložený v případě přerušení a obnovy aktivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Nastavení tlačítka pro návrat zpět v action baru
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Inicializace UI komponent
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        ImageView imageViewDetail = findViewById(R.id.imageViewDetail);
        TextView textViewExplanation = findViewById(R.id.textViewExplanation);

        // Získání dat z intentu a nastavení do UI komponent
        Intent intent = getIntent();
        if (intent != null) {
            String date = intent.getStringExtra("date");
            String title = intent.getStringExtra("title");
            String explanation = intent.getStringExtra("explanation");
            String url = intent.getStringExtra("url");

            textViewDate.setText(date);
            textViewTitle.setText(title);
            textViewExplanation.setText(explanation);

            Glide.with(this).load(url).into(imageViewDetail);

            // Nastavení titulku aktivity podle data obrázku
            setTitle("APOD from " + date);
        }
    }

    /**
     * Metoda onOptionsItemSelected je volána při výběru položky z menu.
     * @param item Vybraná položka z menu.
     * @return true pokud byla položka zpracována, jinak zavolá super metodu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
