package com.dev.dawaswift.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dev.common.models.custom.Resource
import com.dev.dawaswift.data.CartRepository
import com.dev.dawaswift.models.cart.CartItem
import com.dev.dawaswift.models.cart.CartResponse

class CartViewModel(application: Application) : AndroidViewModel(application) {

    internal var cartRepository: CartRepository


    private val cartItemsObservable = MediatorLiveData<Resource<CartResponse>>()
    private val cartActionsObservable = MediatorLiveData<Resource<CartResponse>>()


    init {

        cartRepository = CartRepository(application)


    }


    fun getCartItems() {


    }

    fun observeCartItems(): LiveData<Resource<CartResponse>> {

        return cartItemsObservable
    }

    fun observeCartActions(): LiveData<Resource<CartResponse>> {

        return cartActionsObservable
    }

    fun deleteCartItem(id: Int?, cartId: Int?) {

    }

    fun add(id: Int?, cartId: Int?) {

    }

    fun remove(id: Int?, cartId: Int?) {

    }


    fun pay(cartItemsData: CartResponse) {
        // orderRepository.doPay(cartItemsData)

    }


    fun updateCart(cartNow: CartItem?) {

    }


}

