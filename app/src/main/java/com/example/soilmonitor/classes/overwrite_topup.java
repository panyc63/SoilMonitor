package com.example.soilmonitor.classes;

public class overwrite_topup {
    private boolean topping;

    public boolean isTopping() {
        return topping;
    }

    public void setTopping(boolean topping) {
        this.topping = topping;
    }

    public overwrite_topup(boolean topping) {
        this.topping = topping;
    }
}
