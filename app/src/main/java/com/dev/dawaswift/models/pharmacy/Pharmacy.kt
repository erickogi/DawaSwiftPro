package com.dev.dawaswift.models.pharmacy

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Pharmacy : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("tel")
    @Expose
    var tel: String? = null
    @SerializedName("lat")
    @Expose
    var lat: String? = null
    @SerializedName("lon")
    @Expose
    var lon: String? = null
    @SerializedName("countryId")
    @Expose
    var countryId: String? = null
    @SerializedName("countyId")
    @Expose
    var countyId: String? = null
    @SerializedName("constituencyId")
    @Expose
    var constituencyId: String? = null
    @SerializedName("wardId")
    @Expose
    var wardId: String? = null
    @SerializedName("isOpen")
    @Expose
    var isOpen: String? = null
    @SerializedName("timeStamp")
    @Expose
    var timeStamp: String? = null
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
}