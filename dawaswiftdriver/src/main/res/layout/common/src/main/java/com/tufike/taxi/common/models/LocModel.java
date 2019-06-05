package com.tufike.taxi.common.models;

public class LocModel {

    private Double longitude, latitude;
    private Double altitude;
    private Long time;
    private Long speed,   bearing,  accuracy;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }




    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getBearing() {
        return bearing;
    }

    public void setBearing(Long bearing) {
        this.bearing = bearing;
    }

    public Long getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Long accuracy) {
        this.accuracy = accuracy;
    }
}
