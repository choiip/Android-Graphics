package com.bennyplo.android_mooc_graphics_3d;

public class MovingAngle {
    public double getAngle() {
        return angle;
    }

    private double angle;
    private double angleMin;
    private double angleMax;
    private double delta;

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isPause() {
        return pause;
    }

    private boolean pause = false;

    public MovingAngle(double angleMin, double angleMax, double delta) {
        this.angleMin = angleMin;
        this.angleMax = angleMax;
        this.delta = delta;
    }

    public void advance() {
        if (pause) return;
        angle += delta;
        if (angle > angleMax) {
            angle = angleMax;
            delta *= -1;
        } else if (angle < angleMin) {
            angle = angleMin;
            delta *= -1;
        }
    }
}
