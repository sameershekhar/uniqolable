package com.example.sameershekhar.uniqolable.views;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sameershekhar.uniqolable.R;
import com.example.sameershekhar.uniqolable.util.Utils;
import com.example.sameershekhar.uniqolable.adapters.WeatherAdapter;
import com.example.sameershekhar.uniqolable.models.Weather;
import com.example.sameershekhar.uniqolable.viewmodels.MainActivityViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 100;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.todayImage)
    ImageView todayImage;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.todayTemp)
    TextView todayTemp;

    @BindView(R.id.todayDes)
    TextView todayDes;


    private WeatherAdapter weatherAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100 * 1000)
                .setFastestInterval(100 * 1000);


        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        weatherAdapter=new WeatherAdapter(this);
        recyclerView.setAdapter(weatherAdapter);


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getWeather().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if(weather!=null){
                    //todaytemp.setText(weather.getList().get(0).);
                    progressBar.setVisibility(View.GONE);
                    todayTemp.setText(Utils.getTemp(weather.getList().get(0).getMain().getTemp().toString()));
                    todayDes.setText(weather.getList().get(0).getWeather().get(0).getDescription().toString());
                    todayImage.setImageResource(Utils.getImage(weather.getList().get(0).getWeather().get(0).getMain()));
                    weatherAdapter.setData(weather);
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);

            return;
        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


                } else {

                }
                return;
            }

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {

            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        if(location!=null){
            mainActivityViewModel.fetchDataFromServer(location.getLatitude(),location.getLongitude());

        }else {
            Toast.makeText(MainActivity.this,"null location",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }


}
