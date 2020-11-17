package com.example.soilmonitor.classes;

public class plantAdd {

    private String plantName;
    private String sensorID;

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public plantAdd(String plantName, String sensorID) {
        this.plantName = plantName;
        this.sensorID = sensorID;
    }

    public plantAdd() {
    }
}
