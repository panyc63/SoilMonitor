package com.example.soilmonitor.classes;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {

    private ManualMode ManualMode;
    private AutoMode1 AutoMode1;
    private AutoMode2 AutoMode2;
    private AutoMode3 AutoMode3;
    public Map<String, Boolean> uploadData = new HashMap<>();


    public Post(ManualMode manualMode,AutoMode1 autoMode1, AutoMode2 autoMode2, AutoMode3 autoMode3) {
        ManualMode = manualMode;
        AutoMode1 = autoMode1;
        AutoMode2 = autoMode2;
        AutoMode3 = autoMode3;
    }

    public Post() {
    }

    public com.example.soilmonitor.classes.ManualMode getManualMode() {
        return ManualMode;
    }

    public void setManualMode(com.example.soilmonitor.classes.ManualMode manualMode) {
        ManualMode = manualMode;
    }

    public com.example.soilmonitor.classes.AutoMode1 getAutoMode1() {
        return AutoMode1;
    }

    public void setAutoMode1(com.example.soilmonitor.classes.AutoMode1 autoMode1) {
        AutoMode1 = autoMode1;
    }

    public com.example.soilmonitor.classes.AutoMode2 getAutoMode2() {
        return AutoMode2;
    }

    public void setAutoMode2(com.example.soilmonitor.classes.AutoMode2 autoMode2) {
        AutoMode2 = autoMode2;
    }

    public com.example.soilmonitor.classes.AutoMode3 getAutoMode3() {
        return AutoMode3;
    }

    public void setAutoMode3(com.example.soilmonitor.classes.AutoMode3 autoMode3) {
        AutoMode3 = autoMode3;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ManualMode",getManualMode());
        result.put("AutoMode1",getAutoMode1());
        result.put("AutoMode2", getAutoMode2());
        result.put("AutoMode3", getAutoMode3());
        return result;
    }

}
