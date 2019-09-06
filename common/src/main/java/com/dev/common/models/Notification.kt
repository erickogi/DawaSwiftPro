package com.dev.common.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Notification {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("senderId")
    @Expose
    var senderId: Int? = null
    @SerializedName("receiverId")
    @Expose
    var receiverId: Int? = null
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("resourceId")
    @Expose
    var resourceId: Int? = null
    @SerializedName("isSeen")
    @Expose
    var isSeen: String? = null
    @SerializedName("isRead")
    @Expose
    var isRead: String? = null

    @SerializedName("createdOn")
    @Expose
    var createdOn: String? = null

}