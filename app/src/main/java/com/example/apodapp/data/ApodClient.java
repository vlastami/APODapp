package com.example.apodapp.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apodapp.ui.ApodAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída ApodClient poskytuje metody pro práci s NASA APOD API
 * a lokální SQLite databází pro cachování dat.
 */
public class ApodClient {
    private static final String BASE_URL = "https://api.nasa.gov/";
    private static Retrofit retrofit = null;
    private final DatabaseHelper dbHelper;
    private final Context context;

    /**
     * Konstruktor třídy ApodClient inicializuje DatabaseHelper pro přístup k SQLite databázi.
     * @param context Kontext aplikace, použitý pro inicializaci DatabaseHelper.
     */
    public ApodClient(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    /**
     * Vrací instanci klienta Retrofit pro komunikaci s API.
     * @return Instance klienta Retrofit .
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Načte cachované obrázky APOD z lokální SQLite databáze pro zadané datové rozmezí.
     * @param startDate Počáteční datum ve formátu YYYY-MM-DD.
     * @param endDate Koncové datum ve formátu YYYY-MM-DD.
     * @return Seznam cache obrázků APOD.
     */
    public List<ApodImage> getCachedApodImages(String startDate, String endDate) {
        List<ApodImage> images = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("apod_cache", new String[]{"date", "title", "explanation", "url"}, "date BETWEEN ? AND ?", new String[]{startDate, endDate}, null, null, "date DESC");

        int dateIndex = cursor.getColumnIndex("date");
        int titleIndex = cursor.getColumnIndex("title");
        int explanationIndex = cursor.getColumnIndex("explanation");
        int urlIndex = cursor.getColumnIndex("url");

        if (dateIndex == -1 || titleIndex == -1 || explanationIndex == -1 || urlIndex == -1) {
            throw new IllegalArgumentException("Database schema has changed or query is malformed.");
        }

        while (cursor.moveToNext()) {
            ApodImage image = new ApodImage();
            image.setDate(cursor.getString(dateIndex));
            image.setTitle(cursor.getString(titleIndex));
            image.setExplanation(cursor.getString(explanationIndex));
            image.setUrl(cursor.getString(urlIndex));
            images.add(image);
        }
        cursor.close();
        db.close();
        return images;
    }

    /**
     * Načte obrázky APOD z cache nebo API a nastaví je do adaptéru.
     * @param adapter Adaptér pro zobrazení obrázků APOD.
     * @param startDate Počáteční datum ve formátu YYYY-MM-DD.
     * @param endDate Koncové datum ve formátu YYYY-MM-DD.
     */
    public void loadImages(ApodAdapter adapter, String startDate, String endDate) {
        List<ApodImage> cachedImages = getCachedApodImages(startDate, endDate);
        if (!cachedImages.isEmpty()) {
            adapter.setApodImages(cachedImages);
        } else {
            ApodApi apiService = getClient().create(ApodApi.class);
            Call<List<ApodImage>> call = apiService.getRecentApodImages("pU88E2h1jB0BUrndCebzycZIsd4OxEWyjOqNdZe8", startDate, endDate);
            call.enqueue(new Callback<List<ApodImage>>() {
                @Override
                public void onResponse(Call<List<ApodImage>> call, Response<List<ApodImage>> response) {
                    if (response.isSuccessful()) {
                        List<ApodImage> images = response.body();
                        cacheApodImages(images);
                        adapter.setApodImages(images);
                    } else {
                        Log.e("ApodClient", "Failed to fetch images: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<ApodImage>> call, Throwable t) {
                    Log.e("ApodClient", "Error fetching recent APOD images", t);
                }
            });
        }
    }

    /**
     * Uloží seznam obrázků APOD do cache v lokální SQLite databázi.
     * @param images Seznam obrázků APOD k uložení.
     */
    public void cacheApodImages(List<ApodImage> images) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (ApodImage image : images) {
            ContentValues values = new ContentValues();
            values.put("date", image.getDate());
            values.put("title", image.getTitle());
            values.put("explanation", image.getExplanation());
            values.put("url", image.getUrl());
            db.insert("apod_cache", null, values);
        }
        db.close();
    }
}
