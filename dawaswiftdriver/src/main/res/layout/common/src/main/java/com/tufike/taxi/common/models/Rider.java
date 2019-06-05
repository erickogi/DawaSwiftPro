package com.tufike.taxi.common.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tufike.taxi.common.BR;

public class Rider extends BaseObservable {
    public long id;
    @Expose
    @SerializedName("first_name")
    public String firstName;

    @Expose
    @SerializedName("last_name")
    public String lastName;

    public Media media;

    @Expose
    @SerializedName("mobile_number")
    public long mobileNumber;

    public String status;

    @Expose
    public String email;

    @Expose
    public Gender gender;

    @Expose
    @SerializedName("balance")
    private Double balance;

    @Expose
    public String address;

    public static Rider fromJson(String json) {
        return (new GsonBuilder()).create().fromJson(json, Rider.class);
    }

    public static String toJson(Rider rider) {
        return (new GsonBuilder().excludeFieldsWithoutExposeAnnotation()).create().toJson(rider);
    }
    @Bindable
    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(com.tufike.taxi.common.BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(com.tufike.taxi.common.BR.lastName);
    }

    @Bindable
    public long getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
        notifyPropertyChanged(com.tufike.taxi.common.BR.mobileNumber);
    }

    @Bindable
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(com.tufike.taxi.common.BR.address);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Bindable
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(com.tufike.taxi.common.BR.email);
    }

    @Bindable
    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
        notifyPropertyChanged(BR.media);
    }

    public Double getBalance() {
        return balance;
    }
}
