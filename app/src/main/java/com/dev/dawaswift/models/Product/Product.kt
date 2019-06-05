package com.dev.dawaswift.models.Product

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Product : Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("pharmacyId")
    @Expose
    var pharmacyId: Int? = null
    @SerializedName("pharmacyName")
    @Expose
    var pharmacyName: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("images")
    @Expose
    var images: List<String>? = null
    @SerializedName("details")
    @Expose
    var details: List<String>? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null
    @SerializedName("discountCash")
    @Expose
    var discountCash: Int? = null
    @SerializedName("discountPercentage")
    @Expose
    var discountPercentage: Int? = null
    @SerializedName("discountedPrice")
    @Expose
    var discountedPrice: Int? = null

    @SerializedName("isFavorite")
    @Expose
    var isFavorite: Boolean? = null

    @SerializedName("rating")
    @Expose
    var rating: String? = null




}