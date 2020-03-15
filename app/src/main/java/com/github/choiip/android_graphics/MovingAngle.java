package com.github.choiip.android_graphics;

public class MovingAngle {
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
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
        if (angle >= angleMax) {
            angle = angleMax;
            delta *= -1;
        } else if (angle <= angleMin) {
            angle = angleMin;
            delta *= -1;
        }
    }
}
