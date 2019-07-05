package com.dev.dawaswift.models.Product

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Tags {

    @SerializedName("typeId")
    @Expose
    var typeId: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
}