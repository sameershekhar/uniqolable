package com.example.sameershekhar.uniqolable.network;


import com.example.sameershekhar.uniqolable.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherServiceApi {

    @GET("data/2.5/forecast?")
    Call<Weather> getWeatherDataByCityName(@Query("lat") double lat, @Query("lon") double lon, @Query("units") String units, @Query("appid") String app_id);

}
