package com.dev.dawaswift.models.Cart

import com.dev.dawaswift.models.Order.OrderItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CartItems {


    @SerializedName("totalPrice")
    @Expose
    var totalPrice: Int? = null
    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("discount")
    @Expose
    var discount: Int? = null


    @SerializedName("data")
    @Expose
    var data: List<OrderItem>? = null
}