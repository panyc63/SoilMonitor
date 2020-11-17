package com.example.soilmonitor.classes;

public class ManualMode {
    private boolean active;
    private int top_up;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTop_up() {
        return top_up;
    }

    public void setTop_up(int top_up) {
        this.top_up = top_up;
    }

    public ManualMode(boolean active, int top_Up) {
        this.active = active;
        this.top_up = top_Up;
    }

    public ManualMode() {
    }
}
