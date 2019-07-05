package com.dev.dawaswift.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CartItem {


    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("productId")
    @Expose
    var productId: Int? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("scanCode")
    @Expose
    var scanCode: Any? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null

    @SerializedName("discountedPrice")
    @Expose
    var discountedPrice: Int? = null

}