package com.dev.cabinzz.ui.main

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dev.common.data.Constants
import com.dev.common.utils.CommonUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Cart.AddItem
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.ui.RoundedBottomSheetDialogFragment
import com.dev.dawaswift.ui.productview.ProductView
import kotlinx.android.synthetic.main.add_item_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*


class MenuFragment : RoundedBottomSheetDialogFragment() {

    var product: Product? = null
    var addItem: AddItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()
    }


    fun init (){

        product=arguments?.getSerializable(Constants().PRODUCT) as Product
        if(product!=null){

        }


        addItem = AddItem(product?.id, quantity.text.toString().toInt())



        add.setOnClickListener { calc(ACTION.ADD) }


        remove.setOnClickListener { calc(ACTION.REMOVE) }

        cancel.setOnClickListener { (activity as ProductView).dismissSheet() }

        okay.setOnClickListener {

        }


        price.text = "Ksh   " + product?.discountedPrice.toString()
        CommonUtils().loadImage(context as  Context,product?.image,image)

    }

    enum class ACTION {
        ADD,
        REMOVE
    }

    private fun calc(action: ACTION) {
        val gty = quantity.text.toString()

        if (!TextUtils.isEmpty(quantity.text.toString())) {


            if (action == ACTION.ADD) {
                val vq = Integer.valueOf(gty) + 1
                if (vq <= 10) {
                    quantity.text = vq.toString()
                }


            } else {
                val vq = Integer.valueOf(gty)
                if (vq != 1) {
                    quantity.text = (vq - 1).toString()
                }
            }


        } else {
            quantity.text = "1"
        }

        addItem?.quantity = quantity.text.toString().toInt()

    }

}