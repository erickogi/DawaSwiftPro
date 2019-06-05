package com.dev.dawaswift.models.checkout

import com.dev.dawaswift.models.Address.Address
import com.dev.dawaswift.models.Billing.Billing
import com.dev.dawaswift.models.Cart.CartItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CheckOut {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("cartId")
    @Expose
    var cartId: String? = null


    @SerializedName("cartItem")
    @Expose
    var cartItem: CartItem? = null


    @SerializedName("address")
    @Expose
    var address: Address? = null


    @SerializedName("billing")
    @Expose
    var billing: Billing? = null








}