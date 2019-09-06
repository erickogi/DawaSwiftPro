package com.dev.common.models.driver.balance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Balance {

    @SerializedName("trips")
    @Expose
    var trips: Int? = null
    @SerializedName("earnings")
    @Expose
    var earnings: Int? = null
    @SerializedName("distance")
    @Expose
    var distance: Double? = null
}