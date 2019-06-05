package com.tufike.taxi.common.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tufike.taxi.common.BR;

import java.io.Serializable;
import java.sql.Timestamp;

public class Driver extends BaseObservable implements Serializable{
	@SerializedName("registration_timestamp")
	private Timestamp registrationTimestamp;

	@SerializedName("account_number")
	private String accountNumber;

	@SerializedName("media")
    private Media media;

	@SerializedName("car_media")
    private Media carMedia;

	@Expose
	@SerializedName("car_plate")
	private String carPlate;

    @Expose
	@SerializedName("address")
	private String address;

    @Expose
	@SerializedName("gender")
	private Gender gender;

	@SerializedName("rating")
	private Integer rating;

	@SerializedName("info_changed")
	private int infoChanged;

    @Expose
	@SerializedName("last_name")
	private String lastName;

	@SerializedName("review_count")
	private int reviewCount;

    @Expose
	@SerializedName("car_color")
	private String carColor;

	@SerializedName("certificate_number")
	private String certificateNumber;

	@SerializedName("password")
	private String password;

	@SerializedName("balance")
	private Double balance;

	@SerializedName("car_production_year")
	private Integer carProductionYear;

	@SerializedName("id")
	private int id;

	@SerializedName("mobile_number")
	private long mobileNumber;

    @Expose
	@SerializedName("first_name")
	private String firstName;

	@SerializedName("car")
	private Car car;

    @Expose
	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

    public Timestamp getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(Timestamp registrationTimestamp) {
        this.registrationTimestamp = registrationTimestamp;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Bindable
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
        notifyPropertyChanged(BR.media);
    }

    @Bindable
    public Media getCarMedia() {
        return carMedia;
    }

    public void setCarMedia(Media carMedia) {
        this.carMedia = carMedia;
        notifyPropertyChanged(BR.carMedia);
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        notifyPropertyChanged(com.tufike.taxi.common.BR.gender);
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public boolean isInfoChanged() {
        return infoChanged == 1;
    }

    public void setInfoChanged(boolean infoChanged) {
        this.infoChanged = infoChanged ? 1 : 0;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getCarProductionYear() {
        return carProductionYear;
    }

    public void setCarProductionYear(Integer carProductionYear) {
        this.carProductionYear = carProductionYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toJson(){
        GsonBuilder b = new GsonBuilder();
        Gson gson = b.excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}