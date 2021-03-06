package com.dev.common.models.oauth

import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Oauth : Serializable {
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
    var profile: Profile? = null


    @Ignore
    constructor(profile: Profile?) {
        this.profile = profile
    }

    constructor()


}