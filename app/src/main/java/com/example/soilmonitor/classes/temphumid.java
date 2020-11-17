package com.example.soilmonitor.classes;

public class temphumid {

    private int temperature;
    private int humidity;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public temphumid(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
