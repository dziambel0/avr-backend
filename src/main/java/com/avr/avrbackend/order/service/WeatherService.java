package com.avr.avrbackend.order.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String API_KEY = "0b28e961f041d5898c77bdeb73d755b0";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public String getWeatherData(String city) throws Exception{
        String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return content.toString();
    }
}
