package com.tufike.taxi.common.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.tufike.taxi.common.utils.LatLngDeserializer;

import java.io.Serializable;
import java.sql.Timestamp;

public class TravelSerializable implements Serializable {
    @SerializedName("driver_id")
    private Integer driverId;

    @SerializedName("cost")
    private Double cost;

    @SerializedName("destination_address")
    private String destinationAddress;

    @SerializedName("start_timestamp")
    private Timestamp startTimestamp;

    @SerializedName("log")
    private String log;

    @SerializedName("distance_best")
    private Integer distanceBest;

    @SerializedName("rating")
    private Integer rating;

    @SerializedName("is_hidden")
    private Integer isHidden;

    @SerializedName("pickup_address")
    private String pickupAddress;

    @SerializedName("finish_timestamp")
    private Timestamp finishTimestamp;

    @SerializedName("request_time")
    private Timestamp requestTime;

    @SerializedName("duration_best")
    private Integer durationBest;

    @SerializedName("cost_best")
    private Double costBest;

    @SerializedName("duration_real")
    private Integer durationReal = 0;

    @SerializedName("distance_real")
    private Integer distanceReal = 0;

    @SerializedName("id")
    private Integer id;

    @SerializedName("rider_id")
    private Integer riderId;

    @SerializedName("status")
    private String status;

    private String imageUrl;

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Integer getDistanceBest() {
        return distanceBest;
    }

    public void setDistanceBest(int distanceBest) {
        this.distanceBest = distanceBest;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public Timestamp getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(Timestamp finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getDurationBest() {
        return durationBest;
    }

    public void setDurationBest(int durationBest) {
        this.durationBest = durationBest;
    }

    public Double getCostBest() {
        return costBest;
    }

    public void setCostBest(Double costBest) {
        this.costBest = costBest;
    }

    public Integer getDurationReal() {
        return durationReal;
    }

    public void setDurationReal(int durationReal) {
        this.durationReal = durationReal;
    }

    public Integer getDistanceReal() {
        return distanceReal;
    }

    public void setDistanceReal(int distanceReal) {
        this.distanceReal = distanceReal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        //notifyPropertyChanged(BR.i);
    }

    public static TravelSerializable fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LatLng.class, new LatLngDeserializer());
        Gson customGson = gsonBuilder.create();
        return customGson.fromJson(json,TravelSerializable.class);
    }

}
