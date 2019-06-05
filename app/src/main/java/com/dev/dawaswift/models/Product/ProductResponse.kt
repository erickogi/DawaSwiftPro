package com.dev.dawaswift.models.Product

import com.dev.dawaswift.models.category.HealthArea
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ProductResponse {


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
    var data: HealthArea? = null
}