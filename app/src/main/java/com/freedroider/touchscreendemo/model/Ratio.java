package com.freedroider.touchscreendemo.model;

public class Ratio {

    private int width;
    private int height;

    public Ratio(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Ratio() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
