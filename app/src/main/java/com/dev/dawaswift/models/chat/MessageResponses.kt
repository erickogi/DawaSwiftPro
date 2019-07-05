package com.dev.dawaswift.models.chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MessageResponses {


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
    var data: List<Message>? = null
}