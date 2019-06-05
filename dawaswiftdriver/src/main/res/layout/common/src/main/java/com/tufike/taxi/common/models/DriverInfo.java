package com.tufike.taxi.common.models;

public class DriverInfo {
    public Driver driver;
    public Double distance;
    public Integer duration;
    public Double cost;

    public DriverInfo(Driver driver, int distance, int duration, Double cost) {
        this.driver = driver;
        this.distance = (double)distance / 1000;
        this.duration = duration / 60;
        this.cost = cost;
    }
}
