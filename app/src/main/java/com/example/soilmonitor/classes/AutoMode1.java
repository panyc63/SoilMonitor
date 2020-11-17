package com.example.soilmonitor.classes;

public class AutoMode1 {
    private boolean active;
    private int above_level;
    private int top_up;

    public AutoMode1(boolean active, int above_level, int top_up) {
        this.active = active;
        this.above_level = above_level;
        this.top_up = top_up;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAbove_level() {
        return above_level;
    }

    public void setAbove_level(int above_level) {
        this.above_level = above_level;
    }

    public int getTop_up() {
        return top_up;
    }

    public void setTop_up(int top_up) {
        this.top_up = top_up;
    }

    public AutoMode1() {
    }
}
