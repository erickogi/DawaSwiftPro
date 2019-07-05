package com.dev.dawaswift.models.Order

import com.dev.dawaswift.models.Product.Product
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class OrderItem : Serializable {
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


    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("orderDocNo")
    @Expose
    var orderDocNo: String? = null
    @SerializedName("productId")
    @Expose
    var productId: Int? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null
    @SerializedName("discountCash")
    @Expose
    var discountCash: String? = null
    @SerializedName("discountPercentage")
    @Expose
    var discountPercentage: String? = null
    @SerializedName("discountedPrice")
    @Expose
    var discountedPrice: String? = null
    @SerializedName("productModel")
    @Expose
    var productModel: Product? = null
    @SerializedName("shopId")
    @Expose
    var shopId: Int? = null
    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null



}