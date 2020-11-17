package com.example.soilmonitor.classes;

public class PlantData {

    private boolean topping;
    private int Moisture_Level;
    private String PlantName;
    private String Owner_Email;
    private int temperature;
    private int humidity;

    public boolean isTopping() {
        return topping;
    }

    public void setTopping(boolean topping) {
        this.topping = topping;
    }

    public int getMoisture_Level() {
        return Moisture_Level;
    }

    public void setMoisture_Level(int moisture_Level) {
        Moisture_Level = moisture_Level;
    }

    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    public String getOwner_Email() {
        return Owner_Email;
    }

    public void setOwner_Email(String owner_Email) {
        Owner_Email = owner_Email;
    }

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

    public PlantData() {
    }

    public PlantData(boolean topping, int moisture_Level, String plantName, String owner_Email, int temperature, int humidity) {
        this.topping = topping;
        Moisture_Level = moisture_Level;
        PlantName = plantName;
        Owner_Email = owner_Email;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
