package com.example.juhyang.danbi_watering_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JuHyang on 2016-02-16.
 */
public class Weather_DailyThreeHourWeather extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dailythreehourweather, container, false);
    }
    public void aboutView(){
        TextView textView = (TextView)getView().findViewById(R.id.time);
        ImageView imageView = (ImageView)getView().findViewById(R.id.imageWeather);
        TextView textView1 = (TextView)getView().findViewById(R.id.temperature);

        textView.setText("시간");
        imageView.setImageResource(R.mipmap.ic_launcher);
        textView1.setText("온도");
    }
}

