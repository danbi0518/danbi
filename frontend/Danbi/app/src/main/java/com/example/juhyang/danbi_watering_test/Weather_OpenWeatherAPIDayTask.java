package com.example.juhyang.danbi_watering_test;

import android.os.AsyncTask;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_OpenWeatherAPIDayTask extends AsyncTask<String, Void, Weather_WeatherToday> {
    @Override
    public Weather_WeatherToday doInBackground(String... params) {
        Weather_OpenWeatherAPIClient client = new Weather_OpenWeatherAPIClient();
        String cityNameString = params[0];
        // API 호출
        Weather_WeatherToday w = client.getWeatherToday(cityNameString);

        return w;
    }
}