/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.dev.dawaswift.adapters.product


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Product.Product

class ProductAdapter(
    private var context: Context,
    private var modelList: List<Product>?,
    private val lst: OnRecyclerViewItemClick
) : RecyclerView.Adapter<ProductItemHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder {
        val itemView: View? =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)



        return ProductItemHolder(itemView!!, lst)
    }


    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {

        val model = modelList!![position]
        holder.productname.text = model.name
        holder.productprice.text ="Ksh "+ model.discountedPrice
        CommonUtils().loadImage(context, model.image, holder.image)

        holder.cutprice.text = "ksh" + model.price
        holder.discount.text = "-" + model.discountPercentage+"%"

        holder.discount.visibility = View.VISIBLE
        holder.cutprice.visibility = View.VISIBLE
        holder.productprice.visibility = View.VISIBLE


        if (model.discountPercentage != null && model.discountPercentage!! <= 0) {
            holder.discount.visibility = View.GONE
            holder.cutprice.visibility = View.GONE
        }


        holder.ratingtext.text = "" + model.rating + ""
        holder.ratingbar.rating = model.rating?.toFloat()!!


        if (model.isFavorite != null) {
            if (model.isFavorite!!) {
                holder.fav2.visibility = View.VISIBLE
                holder.fav1.visibility = View.GONE
            } else {
                holder.fav1.visibility = View.VISIBLE
                holder.fav2.visibility = View.GONE
            }
        }

    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun updateList(modelLists: List<Product>?) {

        this.modelList = modelLists
        notifyDataSetChanged()

    }


}