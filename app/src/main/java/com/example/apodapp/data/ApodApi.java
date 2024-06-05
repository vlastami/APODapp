package com.example.apodapp.data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface pro definici webových API volání NASA Astronomy Picture of the Day (APOD).
 * Využívá knihovnu Retrofit pro jednoduchou práci s HTTP požadavky a zpracování odpovědí.
 */
public interface ApodApi {

    /**
     * Získá seznam obrázků APOD za určené datové rozmezí.
     *
     * @param apiKey Klíč pro autorizaci u API služby NASA.
     * @param startDate Počáteční datum pro získání obrázků ve formátu YYYY-MM-DD.
     * @param endDate Koncové datum pro získání obrázků ve formátu YYYY-MM-DD.
     * @return Call objekt obsahující seznam ApodImage objektů, který může být použit pro asynchronní
     *         nebo synchronní získání dat z API.
     */
    @GET("planetary/apod")
    Call<List<ApodImage>> getRecentApodImages(@Query("api_key") String apiKey, @Query("start_date") String startDate, @Query("end_date") String endDate);

}
