package com.dev.common.models.driver.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class DropOffPoint : Serializable {
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("lat")
    @Expose
    var lat: Double? = null
    @SerializedName("lon")
    @Expose
    var lon: Double? = null
}