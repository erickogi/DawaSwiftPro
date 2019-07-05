package com.dev.dawaswift.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class STKResponse {

    @SerializedName("MerchantRequestID")
    @Expose
    var merchantRequestID: String? = null
    @SerializedName("CheckoutRequestID")
    @Expose
    var checkoutRequestID: String? = null
    @SerializedName("ResponseCode")
    @Expose
    var responseCode: String? = null
    @SerializedName("ResponseDescription")
    @Expose
    var responseDescription: String? = null
    @SerializedName("CustomerMessage")
    @Expose
    var customerMessage: String? = null
}