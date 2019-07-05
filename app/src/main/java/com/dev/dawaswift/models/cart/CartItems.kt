package com.dev.dawaswift.models.cart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CartItems {


//    @SerializedName("totalPrice")
//    @Expose
//    var totalPrice: Int? = null
//    @SerializedName("count")
//    @Expose
//    var count: Int? = null
//
//    @SerializedName("discount")
//    @Expose
//    var discount: Int? = null
//
//
//    @SerializedName("data")
//    @Expose
//    var data: List<CartItem>? = null


    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("userId")
    @Expose
    var userId: Int? = null
    @SerializedName("itemsCount")
    @Expose
    var itemsCount: Int? = null
    @SerializedName("totalAmount")
    @Expose
    var totalAmount: Int? = null
    @SerializedName("totalDiscount")
    @Expose
    var totalDiscount: Int? = null
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null
    @SerializedName("firstName")
    @Expose
    var firstName: String? = null
    @SerializedName("lastName")
    @Expose
    var lastName: String? = null
    @SerializedName("cartItems")
    @Expose
    var cartItems: List<CartItem>? = null
}