package com.example.apodapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.apodapp.R;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        ImageView imageViewDetail = findViewById(R.id.imageViewDetail);
        TextView textViewExplanation = findViewById(R.id.textViewExplanation);

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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

