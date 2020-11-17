package com.example.soilmonitor.classes;

import java.sql.Time;

public class AutoMode2 {
    private boolean active;
    private String time;
    private int top_up;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTop_up() {
        return top_up;
    }

    public void setTop_up(int top_up) {
        this.top_up = top_up;
    }

    public AutoMode2() {
    }

    public AutoMode2(boolean active, String time, int top_up) {
        this.active = active;
        this.time = time;
        this.top_up = top_up;
    }
}
