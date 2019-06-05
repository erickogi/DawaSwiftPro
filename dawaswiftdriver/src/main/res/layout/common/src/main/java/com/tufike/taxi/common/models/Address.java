package com.tufike.taxi.common.models;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Address implements Serializable{

	@SerializedName("address")
	private String address;

	/*@SerializedName("location")
	private double[] location;
*/
	@SerializedName("location")
	private LatLng location;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	/*public void setLocation(double[] location){
		this.location = location;
	}

	public double[] getLocation(){
		return location;
	}*/

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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}