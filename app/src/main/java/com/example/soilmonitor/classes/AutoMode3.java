package com.example.soilmonitor.classes;

import java.sql.Time;

public class AutoMode3 {
    private boolean active;
    private String start;
    private int interval;
    private int top_up;

    public AutoMode3() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getTop_up() {
        return top_up;
    }

    public void setTop_up(int top_up) {
        this.top_up = top_up;
    }

    public AutoMode3(boolean active, String start, int interval, int top_up) {
        this.active = active;
        this.start = start;
        this.interval = interval;
        this.top_up = top_up;
    }
}
