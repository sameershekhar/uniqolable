package com.example.sameershekhar.uniqolable.viewmodels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sameershekhar.uniqolable.util.Constant;
import com.example.sameershekhar.uniqolable.models.Weather;
import com.example.sameershekhar.uniqolable.network.RetrofitClient;
import com.example.sameershekhar.uniqolable.network.WeatherServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private WeatherServiceApi wheatherServiceApi;
    private MutableLiveData<Weather> weather=new MutableLiveData<>();


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchDataFromServer(Double lat ,Double lon){
        wheatherServiceApi= RetrofitClient.getInstance().getWeatherService();
        Call<Weather> weatherResponseCall = wheatherServiceApi.getWeatherDataByCityName(lat,lon,Constant.UINTS, Constant.APP_ID);
        weatherResponseCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(response!=null){
//                    Log.v("Tag1", response.body()+"");
//                    Toast.makeText(getApplication().getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();


                    weather.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                weather = null;
//                Log.v("Tag1", t.getMessage()+"");
//                Toast.makeText(getApplication().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public LiveData<Weather> getWeather(){
        return weather;
    }
}
