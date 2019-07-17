package com.dev.common.models.driver.requests

import com.dev.common.models.oauth.Profile
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class TripRequest : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("orderId")
    @Expose
    var orderId: Int? = null
    @SerializedName("clientModel")
    @Expose
    var clientModel: Profile? = null

    @SerializedName("earnings")
    @Expose
    var earnings: Int? = null

    @SerializedName("distance")
    @Expose
    var distance: Double? = null

    @SerializedName("pickUpPoints")
    @Expose
    var pickUpPoints: List<PickUpPoint>? = null

    @SerializedName("dropOffPoint")
    @Expose
    var dropOffPoint: DropOffPoint? = null


    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null
}