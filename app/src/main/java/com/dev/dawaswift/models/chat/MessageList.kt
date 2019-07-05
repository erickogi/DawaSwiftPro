package com.dev.dawaswift.models.chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MessageList {


    @SerializedName("messages")
    @Expose
    var messages: MutableList<Message>? = null


    @SerializedName("message")
    @Expose
    var message: Message? = null


}