//java/com/example/apodapp/data/ApodApi.java
package com.example.apodapp.data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApodApi {

    @GET("planetary/apod")
    Call<List<ApodImage>> getRecentApodImages(@Query("api_key") String apiKey, @Query("start_date") String startDate, @Query("end_date") String endDate);

}