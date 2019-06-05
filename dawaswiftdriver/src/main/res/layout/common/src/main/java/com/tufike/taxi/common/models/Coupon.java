package com.tufike.taxi.common.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Coupon implements Serializable {

	@SerializedName("is_enabled")
	private int isEnabled;

	@SerializedName("many_users_can_use")
	private int manyUsersCanUse;

	@SerializedName("many_times_user_can_use")
	private int manyTimesUserCanUse;

	@SerializedName("flat_discount")
	private double flatDiscount;

	@SerializedName("code")
	private String code;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("start_at")
	private Timestamp startAt;

	@SerializedName("expiration_at")
	private Timestamp expirationAt;

	@SerializedName("discount_percent")
	private int discountPercent;

	@SerializedName("is_first_travel_only")
	private int isFirstTravelOnly;

	private int daysLeft;

	public void setIsEnabled(int isEnabled){
		this.isEnabled = isEnabled;
	}

	public int getIsEnabled(){
		return isEnabled;
	}

	public void setManyUsersCanUse(int manyUsersCanUse){
		this.manyUsersCanUse = manyUsersCanUse;
	}

	public int getManyUsersCanUse(){
		return manyUsersCanUse;
	}

	public void setManyTimesUserCanUse(int manyTimesUserCanUse){
		this.manyTimesUserCanUse = manyTimesUserCanUse;
	}

	public int getManyTimesUserCanUse(){
		return manyTimesUserCanUse;
	}

	public void setFlatDiscount(double flatDiscount){
		this.flatDiscount = flatDiscount;
	}

	public double getFlatDiscount(){
		return flatDiscount;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
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

	public void setStartAt(Timestamp startAt){
		this.startAt = startAt;
	}

	public Timestamp getStartAt(){
		return startAt;
	}

	public void setExpirationAt(Timestamp expirationAt){
		this.expirationAt = expirationAt;
	}

	public Timestamp getExpirationAt(){
		return expirationAt;
	}

	public void setDiscountPercent(int discountPercent){
		this.discountPercent = discountPercent;
	}

	public int getDiscountPercent(){
		return discountPercent;
	}

	public void setIsFirstTravelOnly(int isFirstTravelOnly){
		this.isFirstTravelOnly = isFirstTravelOnly;
	}

	public int getIsFirstTravelOnly(){
		return isFirstTravelOnly;
	}

	public String getDaysLeft() {
		this.daysLeft = (int)((expirationAt.getTime() - (new Date()).getTime()) / (1000 * 60 * 60 * 24));
		return String.valueOf(this.daysLeft);
	}
}