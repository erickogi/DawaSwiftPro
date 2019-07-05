package com.dev.common.models.oauth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class uRL : Serializable {
    @SerializedName("physicalUrl")
    @Expose
    var physicalUrl: String? = null
    @SerializedName("hyperLinkUrl")
    @Expose
    var hyperLinkUrl: String? = null


}