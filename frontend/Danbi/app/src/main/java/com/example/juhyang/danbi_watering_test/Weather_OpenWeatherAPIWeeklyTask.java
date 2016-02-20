package com.example.juhyang.danbi_watering_test;

import android.os.AsyncTask;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_OpenWeatherAPIWeeklyTask extends AsyncTask<String, Void, Weather_WeatherWeekly> {
    @Override
    public Weather_WeatherWeekly doInBackground(String... params) {
        Weather_OpenWeatherAPIClient client = new Weather_OpenWeatherAPIClient();
        String cityNameString = params[0];
        // API 호출
        Weather_WeatherWeekly w = client.getWeatherWeekly(cityNameString);

        return w;
    }
}