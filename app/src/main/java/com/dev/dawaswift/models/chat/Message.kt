package com.dev.dawaswift.models.chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Message : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("pharmacyId")
    @Expose
    var pharmacyId: Int? = null
    @SerializedName("pharmacyName")
    @Expose
    var pharmacyName: String? = null
    @SerializedName("pharmacyImage")
    @Expose
    var pharmacyImage: String? = null
    @SerializedName("custId")
    @Expose
    var custId: Int? = null
    @SerializedName("firebasetoken")
    @Expose
    var firebasetoken: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("hasResource")
    @Expose
    var hasResource: String? = null
    @SerializedName("resourceId")
    @Expose
    var resourceId: String? = null
    @SerializedName("isRead")
    @Expose
    var isRead: String? = null
    @SerializedName("sender")
    @Expose
    var sender: String? = null
    @SerializedName("phamarcistId")
    @Expose
    var phamarcistId: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("createdOn")
    @Expose
    var createdOn: String? = null
    @SerializedName("updatedOn")
    @Expose
    var updatedOn: String? = null


}