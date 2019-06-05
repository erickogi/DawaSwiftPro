package com.dev.dawaswift.models.Billing

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Billing {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: Int? = null

    @SerializedName("billingTypeId")
    @Expose
    var billingTypeId: String? = null
    @SerializedName("billingTypeName")
    @Expose
    var billingTypeName: String? = null
    @SerializedName("billingTypeImage")
    @Expose
    var billingTypeImage: String? = null

    @SerializedName("billingTypeDescription")
    @Expose
    var billingTypeDescription: String? = null



    @SerializedName("mpesaPhone")
    @Expose
    var mpesaPhone: String? = null

    @SerializedName("airtelPhone")
    @Expose
    var airtelPhone: String? = null




    @SerializedName("cardNumber")
    @Expose
    var cardNumber: Int? = null

    @SerializedName("cardExpMonth")
    @Expose
    var cardExpMonth: Int? = null

    @SerializedName("cardExpYear")
    @Expose
    var cardExpYear: Int? = null

    @SerializedName("cardCVC")
    @Expose
    var cardCVC: Int? = null





}