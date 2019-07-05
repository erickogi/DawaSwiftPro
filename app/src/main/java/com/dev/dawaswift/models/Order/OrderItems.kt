package com.dev.dawaswift.models.Order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class OrderItems : Serializable {


    @SerializedName("shopId")
    @Expose
    var shopId: Int? = null
    @SerializedName("docNo")
    @Expose
    var docNo: String? = null
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("userId")
    @Expose
    var userId: Int? = null
    @SerializedName("buyerName")
    @Expose
    var buyerName: String? = ""
    @SerializedName("itemsCount")
    @Expose
    var itemsCount: String? = null
    @SerializedName("totalAmount")
    @Expose
    var totalAmount: String? = null
    @SerializedName("totalDiscount")
    @Expose
    var totalDiscount: String? = null
    @SerializedName("createdOn")
    @Expose
    var createdOn: String? = null
    @SerializedName("orderItems")
    @Expose
    var orderItems: List<OrderItem>? = null

}