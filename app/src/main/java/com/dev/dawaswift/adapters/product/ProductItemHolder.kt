/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.dev.dawaswift.adapters.product
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.google.android.material.card.MaterialCardView

import java.lang.ref.WeakReference


class ProductItemHolder(itemView: View, lst: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {
        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(lst)
    var itemVew: View
    var productItem: MaterialCardView=itemView.findViewById(R.id.linear_product)

    init {
        this.productItem.setOnClickListener(this)
        this.itemVew = itemView
        this.itemVew.setOnClickListener(this)
    }

    var productname: TextView = itemView.findViewById(R.id.productname)
    var image: ImageView = itemView.findViewById(R.id.image)
    var productprice: TextView = itemView.findViewById(R.id.productprice)
    var discount: TextView = itemView.findViewById(R.id.discount)
    var cutprice: TextView = itemView.findViewById(R.id.cutprice)




    var fav1 = itemView.findViewById<View>(R.id.fav1) as ImageView
    var fav2 = itemView.findViewById<View>(R.id.fav2) as ImageView
    var ratingbar = itemView.findViewById<View>(R.id.productratingbar) as RatingBar
    var ratingtext = itemView.findViewById(R.id.productratingtext) as TextView



}
