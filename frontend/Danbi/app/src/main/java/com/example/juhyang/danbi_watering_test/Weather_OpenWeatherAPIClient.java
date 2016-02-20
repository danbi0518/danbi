package com.example.juhyang.danbi_watering_test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_OpenWeatherAPIClient {
    final static String openWeatherURL = "http://api.openweathermap.org/data/2.5/weather";
    final static String cityName = "Seoul";
    final static String appid = "8cbb933c3116dd59f65e17923de6240e";

    public Weather_WeatherToday getWeatherToday(String string) {
        Weather_WeatherToday weatherWeatherToday = new Weather_WeatherToday();
        String urlString = openWeatherURL + "?q=" + cityName + "&units=metric&appid=" + appid;

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON
            weatherWeatherToday = parseJSONToday(json);
            weatherWeatherToday.setCity(cityName);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }

        // set Weather_WeatherToday Object

        return weatherWeatherToday;
    }

    public Weather_WeatherWeekly getWeatherWeekly(String string) {
        Weather_WeatherWeekly weatherWeatherWeekly = new Weather_WeatherWeekly();
        String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily" + "?q=" + cityName + "&units=metric&cnt=7&appid=" + appid;

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON
            weatherWeatherWeekly = parseJSONWeekly(json);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }

        // set Weather_WeatherToday Object

        return weatherWeatherWeekly;
    }

    private Weather_WeatherToday parseJSONToday(JSONObject json) throws JSONException {
        Weather_WeatherToday w = new Weather_WeatherToday();

        w.setMainWeather(getWeathermain(json.getJSONArray("weather").getJSONObject(0).getInt("id")));
        w.setTemprature(json.getJSONObject("main").getInt("temp"));
        w.setHumidity(json.getJSONObject("main").getInt("humidity"));
        w.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));
        w.setWindDeg(getDegree(json.getJSONObject("wind").getInt("deg")));

        return w;
    }

    private Weather_WeatherWeekly parseJSONWeekly(JSONObject json) throws JSONException {
        Weather_WeatherWeekly w = new Weather_WeatherWeekly();
        w.setWeather(getWeatherString(json.getJSONArray("list")));
        w.setDay(getDay(json.getJSONArray("list")));
        w.setMinTemperature(getMinTemperature(json.getJSONArray("list")));
        w.setMaxTemperature(getMaxTemperature(json.getJSONArray("list")));

        return w;
    }

    public String[] getWeatherString(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        String[] weatherString = new String[length];

        for (int i = 0; i < length; i++) {
            weatherString[i] = getWeathermain(jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getInt("id"));
        }
        return weatherString;
    }

    private int[] getMaxTemperature(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        int[] minTemp = new int[length];

        for (int i = 0; i < length; i++)
            minTemp[i] = jsonArray.getJSONObject(i).getJSONObject("temp").getInt("max");

        return minTemp;
    }

    private int[] getMinTemperature(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        int[] minTemp = new int[length];

        for (int i = 0; i < length; i++)
            minTemp[i] = jsonArray.getJSONObject(i).getJSONObject("temp").getInt("min");

        return minTemp;
    }

    private String[] getDay(JSONArray jsonArray) throws JSONException {
        int length = jsonArray.length();
        String[] day = new String[length];

        for (int i = 0; i < length; i++) {
            day[i] = jsonArray.getJSONObject(i).getString("dt");
        }
        return day;
    }

    private String getDegree(int intDegree) {
        String degree;
        if (intDegree == 0 || intDegree == 360)
            degree = "북";
        else if (intDegree == 90)
            degree = "동";
        else if (intDegree == 180)
            degree = "남";
        else if (intDegree == 270)
            degree = "서";
        else if (0 < intDegree && intDegree < 90)
            degree = "북동";
        else if (90 < intDegree && intDegree < 180)
            degree = "남동";
        else if (180 < intDegree && intDegree < 270)
            degree = "남서";
        else
            degree = "북서";

        return degree;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public String getWeathermain(int id) {
        String weatherMain = "모름";

        if (id == 800)
            weatherMain = "맑음";
        else if (500 <= id && id < 600)
            weatherMain = "비";
        else if (600 <= id && id < 700)
            weatherMain = "눈";
        else if (800 < id && id < 900)
            weatherMain = "구름";

        return weatherMain;
    }


}