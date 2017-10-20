package com.freedroider.touchscreendemo.model;

public class ControlAction {

    private float angle;
    private double distance;

    public ControlAction(float angle, double distance) {
        this.angle = angle;
        this.distance = distance;
    }

    public ControlAction() {
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
