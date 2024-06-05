//java/com/example/apodapp/data/ApiClient.java

package com.example.apodapp.data;

        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import android.util.Log;

        import com.example.apodapp.service.ApodImageService;
        import com.example.apodapp.ui.ApodAdapter;

        import java.util.List;

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

    public static void loadImages(ApodAdapter adapter, String startDate, String endDate) {
        String apiKey = "pU88E2h1jB0BUrndCebzycZIsd4OxEWyjOqNdZe8";
        ApodApi apiService = getClient().create(ApodApi.class);
        Call<List<ApodImage>> call = apiService.getRecentApodImages(apiKey, startDate, endDate);
        call.enqueue(new Callback<List<ApodImage>>() {
            @Override
            public void onResponse(Call<List<ApodImage>> call, Response<List<ApodImage>> response) {
                if (response.isSuccessful()) {
                    adapter.setApodImages(response.body());
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
