package com.kisssusha.ksplugin;

import java.util.ArrayList;
import java.util.List;

public class WeatherReport {
    private String location;
    private String currentWeather;
    private List<String> predictWeatherList;

    public WeatherReport() {
        this.predictWeatherList = new ArrayList<>();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String[] getPredictWeatherList() {
        return new String[]{predictWeatherList.toString()};
    }

    public void addToPredictWeatherList(String predictWeather) {
        this.predictWeatherList.add(predictWeather);
    }

    private String arrayToString(List<String> array) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String particle : array) {
            if (!first)
                sb.append(' ');

            sb.append(particle);
            first = false;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return location + "\n" + currentWeather + "\n" + arrayToString(predictWeatherList);
    }
}
