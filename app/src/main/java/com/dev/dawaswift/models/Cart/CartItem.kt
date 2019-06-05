package com.dev.dawaswift.models.Cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CartItem {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
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

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null


    @SerializedName("totalPrice")
    @Expose
    var totalPrice: Int? = null

}