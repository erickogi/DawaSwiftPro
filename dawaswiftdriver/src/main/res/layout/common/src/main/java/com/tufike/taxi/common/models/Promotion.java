package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class Promotion{

	@SerializedName("start_timestamp")
	private Timestamp startTimestamp;

	@SerializedName("expiration_timestamp")
	private Timestamp expirationTimestamp;

	@SerializedName("description")
	private String description;

	@SerializedName("media")
	private Media media;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	private int daysLeft;

	public void setStartTimestamp(Timestamp startTimestamp){
		this.startTimestamp = startTimestamp;
	}

	public Timestamp getStartTimestamp(){
		return startTimestamp;
	}

	public void setExpirationTimestamp(Timestamp expirationTimestamp){
		this.expirationTimestamp = expirationTimestamp;
	}

	public Timestamp getExpirationTimestamp(){
		return expirationTimestamp;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setMedia(Media mediaId){
		this.media = mediaId;
	}

	public Media getMedia(){
		return media;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public int getDaysLeft() {
		Date date = new Date();
		daysLeft = (int)((expirationTimestamp.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
		return daysLeft;

	}
}