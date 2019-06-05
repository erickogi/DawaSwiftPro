package com.dev.dawaswift.models.category

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Category : Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("subCategories")
    @Expose
    var data: List<SubCategory>? = null
}