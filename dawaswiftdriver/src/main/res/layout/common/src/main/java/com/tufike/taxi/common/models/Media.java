package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Media implements Serializable{

	public enum PathType {
		relative,
		absolute
	}
	@SerializedName("address")
	private String address;

	@SerializedName("privacy_level")
	private String privacyLevel;

	@SerializedName("path_type")
	private PathType pathType;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private Object type;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPrivacyLevel(String privacyLevel){
		this.privacyLevel = privacyLevel;
	}

	public String getPrivacyLevel(){
		return privacyLevel;
	}

	public PathType getPathType() {
		return pathType;
	}

	public void setPathType(PathType pathType) {
		this.pathType = pathType;
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

	public void setType(Object type){
		this.type = type;
	}

	public Object getType(){
		return type;
	}
}