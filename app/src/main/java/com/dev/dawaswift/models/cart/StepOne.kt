package com.dev.dawaswift.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StepOne {


    @SerializedName("cost")
    @Expose
    var cost: String? = null
    @SerializedName("period")
    @Expose
    var period: String? = null
}