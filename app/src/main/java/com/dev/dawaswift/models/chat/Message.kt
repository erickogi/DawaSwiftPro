package com.dev.dawaswift.models.chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Message {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("senderId")
    @Expose
    var senderId: String? = null

    @SerializedName("senderName")
    @Expose
    var senderName: String? = null

    @SerializedName("senderImage")
    @Expose
    var senderImage: String? = null



    @SerializedName("receiverId")
    @Expose
    var receiverId: String? = null

    @SerializedName("receiverName")
    @Expose
    var receiverName: String? = null

    @SerializedName("receiverImage")
    @Expose
    var receiverImage: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("keyCode")
    @Expose
    var keyCode: String? = null

    @SerializedName("isRead")
    @Expose
    var isRead: String? = null


    @SerializedName("dateTimeSent")
    @Expose
    var dateTimeSent: String? = null


    @SerializedName("dateTimeRead")
    @Expose
    var dateTimeRead: String? = null










}