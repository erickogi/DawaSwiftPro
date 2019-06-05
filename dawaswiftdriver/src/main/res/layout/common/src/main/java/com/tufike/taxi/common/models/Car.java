package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

public class Car{

	@SerializedName("media")
	private Media media;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
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
}