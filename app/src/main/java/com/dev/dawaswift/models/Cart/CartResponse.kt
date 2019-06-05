package com.dev.dawaswift.models.Cart

import com.dev.dawaswift.models.Order.OrderItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CartResponse {


    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("errors")
    @Expose
    var errors: Any? = null
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
    var data: OrderItem? = null
}