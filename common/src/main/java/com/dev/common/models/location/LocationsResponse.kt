package com.dev.common.models.location

import androidx.room.Ignore
import com.dev.common.models.location.Location
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationsResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("errors")
    @Expose
    var errors: List<String>? = null
    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null
    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Location>? = null


    @Ignore
    constructor(data: List<Location>?) {
        this.data = data
    }

    constructor()
}
