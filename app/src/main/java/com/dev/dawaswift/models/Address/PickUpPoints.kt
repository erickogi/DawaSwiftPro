package com.dev.dawaswift.models.Address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PickUpPoints {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null


    @SerializedName("lat")
    @Expose
    var lat: Double? = null
    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("isDefault")
    @Expose
    var isDefault: Boolean? = false

}