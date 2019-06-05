package com.dev.dawaswift.models.Order

import com.dev.dawaswift.models.Order.OrderItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class OrderItems {


    @SerializedName("totalPrice")
    @Expose
    var totalPrice: Int? = null
    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("discount")
    @Expose
    var discount: Int? = null

    @SerializedName("isPaidFor")
    @Expose
    var isPaidFor: Int? = null

    @SerializedName("orderStatus")
    @Expose
    var orderStatus: Int? = null

    @SerializedName("paymentInformation")
    @Expose
    var paymentInformation: String? = null






    @SerializedName("data")
    @Expose
    var data: List<OrderItem>? = null
}