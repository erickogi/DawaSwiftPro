package com.dev.dawaswift.models.Cart

class AddItem {
    var custId: Int? = null
    var productId: Int? = null
    var quantity: Int? = null

    constructor(productId: Int?, quantity: Int?) {
        this.productId = productId
        this.quantity = quantity
    }

}