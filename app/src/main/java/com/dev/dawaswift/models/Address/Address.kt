package com.dev.dawaswift.models.Address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Address {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null


    @SerializedName("email")
    @Expose
    var email: String? = null


    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null


    @SerializedName("town")
    @Expose
    var town: String? = null


    @SerializedName("countryId")
    @Expose
    var countryId: String? = null

    @SerializedName("stateId")
    @Expose
    var stateId: String? = null


    @SerializedName("townId")
    @Expose
    var townId: String? = null


    @SerializedName("street")
    @Expose
    var street: String? = null

    @SerializedName("zip")
    @Expose
    var zip: String? = null


    @SerializedName("latitude")
    @Expose
    var latitude: String? = null


    @SerializedName("longitude")
    @Expose
    var longitude: String? = null


    @SerializedName("isDefault")
    @Expose
    var isDefault: Boolean? = false




}