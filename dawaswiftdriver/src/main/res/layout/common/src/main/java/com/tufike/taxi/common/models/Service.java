package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable {

	@SerializedName("service_category_id")
	private int serviceCategoryId;

	@SerializedName("media")
	private Media media;

	@SerializedName("available_time_from")
	private String availableTimeFrom;

	@SerializedName("per_hundred_meters")
	private double perHundredMeters;

	@SerializedName("available_time_to")
	private String availableTimeTo;

	@SerializedName("per_minute_passed")
	private double perMinutePassed;

	@SerializedName("base_fare")
	private double baseFare;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("per_minute_wait")
	private double perMinuteWait;

    @SerializedName("cost")
    private double cost;

	public int getServiceCategoryId() {
		return serviceCategoryId;
	}

	public void setServiceCategoryId(int serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}


	public double getPerHundredMeters() {
		return perHundredMeters;
	}

	public void setPerHundredMeters(double perHundredMeters) {
		this.perHundredMeters = perHundredMeters;
	}

	public double getPerMinutePassed() {
		return perMinutePassed;
	}

	public void setPerMinutePassed(double perMinutePassed) {
		this.perMinutePassed = perMinutePassed;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPerMinuteWait() {
		return perMinuteWait;
	}

	public void setPerMinuteWait(double perMinuteWait) {
		this.perMinuteWait = perMinuteWait;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}