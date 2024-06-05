package com.example.apodapp.service;

import com.example.apodapp.data.ApodImage;
import com.example.apodapp.data.ApiClient;
import com.example.apodapp.ui.ApodAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ApodImageService {

    public void loadImagesForLastWeek(ApodAdapter adapter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String endDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        String startDate = dateFormat.format(calendar.getTime());

        ApiClient.loadImages(adapter, startDate, endDate);
    }
}
