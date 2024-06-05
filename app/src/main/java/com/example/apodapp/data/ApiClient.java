package com.example.apodapp.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Context;
import android.util.Log;

import com.example.apodapp.ui.ApodAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ApiClient {
    private static final String BASE_URL = "https://api.nasa.gov/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void loadImagesForLastWeek(Context context, ApodAdapter adapter) {
        String apiKey = "pU88E2h1jB0BUrndCebzycZIsd4OxEWyjOqNdZe8";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String endDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        String startDate = dateFormat.format(calendar.getTime());

        Log.d("ApiClient", "Loading images from " + startDate + " to " + endDate);

        ApodService apiService = getClient().create(ApodService.class);
        Call<List<ApodImage>> call = apiService.getRecentApodImages(apiKey, startDate, endDate);
        call.enqueue(new Callback<List<ApodImage>>() {
            @Override
            public void onResponse(Call<List<ApodImage>> call, Response<List<ApodImage>> response) {
                if (response.isSuccessful()) {
                    List<ApodImage> images = response.body();
                    if (images != null) {
                        Log.d("ApiClient", "Received " + images.size() + " images");
                    } else {
                        Log.d("ApiClient", "No images received");
                    }
                    adapter.setApodImages(images);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("ApiClient", "Failed to fetch images: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ApodImage>> call, Throwable t) {
                Log.e("ApiClient", "Error fetching recent APOD images", t);
            }
        });
    }

}
