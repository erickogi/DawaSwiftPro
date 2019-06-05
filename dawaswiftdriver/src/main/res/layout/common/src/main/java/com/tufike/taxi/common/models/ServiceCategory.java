package com.tufike.taxi.common.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ServiceCategory {

	@SerializedName("cat_title")
	private String catTitle;

	@SerializedName("cat_id")
	private int catId;

	@SerializedName("services")
	private ArrayList<Service> services;

	public void setCatTitle(String catTitle){
		this.catTitle = catTitle;
	}

	public String getCatTitle(){
		return catTitle;
	}

	public void setCatId(int catId){
		this.catId = catId;
	}

	public int getCatId(){
		return catId;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}
}