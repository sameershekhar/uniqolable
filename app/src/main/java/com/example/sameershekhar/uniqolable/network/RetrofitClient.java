package com.example.sameershekhar.uniqolable.network;

import com.example.sameershekhar.uniqolable.util.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Retrofit retrofit;

    private WeatherServiceApi wheatherServiceApi;

    public RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wheatherServiceApi = retrofit.create(WeatherServiceApi.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public WeatherServiceApi getWeatherService() {
        return wheatherServiceApi;
    }
}
