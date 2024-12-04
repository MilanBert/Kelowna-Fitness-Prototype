package com.example.step4test;

public class Goal {
//this is used to generate dummy goals for the long term progress report.
    // Goal properties
    private String name;
    private float target;
    private float current;
    private float start;
    private float trend;

    // Constructor to initialize Goal object
    public Goal(String name, float target, float current, float start) {
        this.name = name;
        this.target = target;
        this.current = current;
        this.start = start;
        this.trend = (current-target)/(start-target);
    }

    // Getters for the goal properties
    public String getName() {
        return name;
    }

    public float getTarget() {
        return target;
    }

    public float getCurrent() {
        return current;
    }

    public float getStart() {
        return start;
    }

    public float getTrend() {
        return trend;
    }
}
