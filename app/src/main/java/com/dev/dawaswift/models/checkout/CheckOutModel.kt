package com.dev.dawaswift.models.checkout

import com.dev.dawaswift.models.Address.Address
import com.dev.dawaswift.models.Billing.Billing
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CheckOutModel {


    @SerializedName("amount")
    @Expose
    var amount: String? = null


    @SerializedName("mpesaPhone")
    @Expose
    var mpesaPhone: String? = null


    @SerializedName("billingTypeId")
    @Expose
    var billingTypeId: String? = null


    @SerializedName("billing")
    @Expose
    var billing: Billing? = null


    @SerializedName("address")
    @Expose
    var address: Address? = null


    constructor(amount: String?, mpesaPhone: String?, billingTypeId: String?) {
        this.amount = amount
        this.mpesaPhone = mpesaPhone
        this.billingTypeId = billingTypeId
    }
}