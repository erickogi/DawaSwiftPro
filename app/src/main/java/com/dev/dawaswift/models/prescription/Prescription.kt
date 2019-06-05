package com.dev.dawaswift.models.prescription

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Prescription {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("dateTimeSent")
    @Expose
    var dateTimeSent: String? = null


    @SerializedName("respondedTo")
    @Expose
    var respondedTo: Int? = null


    @SerializedName("response")
    @Expose
    var response: Int? = null

    @SerializedName("keyCode")
    @Expose
    var keyCode: String? = null





}