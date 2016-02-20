package com.example.juhyang.danbi_watering_test;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_WeatherThreeHour {
    String[] time;
    int[] temperature;
    String[] weather;

    public void setTemperature(int[] temperature) {
        this.temperature = temperature;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public void setWeather(String[] weather) {
        this.weather = weather;
    }

    public int[] getTemperature() {
        return temperature;
    }

    public String[] getTime() {
        return time;
    }

    public String[] getWeather() {
        return weather;
    }
}
