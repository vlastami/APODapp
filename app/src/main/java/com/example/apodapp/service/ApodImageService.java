package com.example.apodapp.service;

import com.example.apodapp.data.ApodClient;
import com.example.apodapp.ui.ApodAdapter;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Třída ApodImageService poskytuje servisní vrstvu pro načítání obrázků NASA APOD.
 * Tato třída zajišťuje logiku pro načítání dat za poslední týden.
 */
public class ApodImageService {
    private final ApodClient apodClient;  // Instance ApodClient pro práci s API a databází

    /**
     * Konstruktor třídy ApodImageService.
     * @param context Kontext aplikace, použitý pro inicializaci ApodClient.
     */
    public ApodImageService(Context context) {
        this.apodClient = new ApodClient(context);
    }

    /**
     * Načte obrázky APOD za poslední týden a nastaví je do adaptéru.
     * @param adapter Adaptér pro zobrazení obrázků APOD.
     */
    public void loadImagesForLastWeek(ApodAdapter adapter) {
        // Formátování datumu
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String endDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        String startDate = dateFormat.format(calendar.getTime());

        // Načtení obrázků pomocí ApodClient
        apodClient.loadImages(adapter, startDate, endDate);
    }
}
