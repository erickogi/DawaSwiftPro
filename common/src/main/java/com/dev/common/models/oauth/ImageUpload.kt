package com.dev.common.models.oauth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ImageUpload : Serializable {
    @SerializedName("urls")
    @Expose
    var urls: List<uRL>? = null


}