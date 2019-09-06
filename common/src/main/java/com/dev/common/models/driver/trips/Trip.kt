package com.dev.common.models.driver.trips

import com.dev.common.models.driver.requests.TripRequest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Trip : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("driverId")
    @Expose
    var driverId: Int? = null
    @SerializedName("orderId")
    @Expose
    var orderId: Int? = null
    @SerializedName("beginDateTime")
    @Expose
    var beginDateTime: String? = null
    @SerializedName("endDateTime")
    @Expose
    var endDateTime: String? = null
    @SerializedName("earnings")
    @Expose
    var earnings: String? = null


    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null


    @SerializedName("tripData")
    @Expose
    var tripData: TripRequest? = null


}