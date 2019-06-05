/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.dev.dawaswift.adapters.category
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R

import java.lang.ref.WeakReference


class CategoryItemHolder(itemView: View, lst: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {
        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(lst)


    var itemVew: View = itemView

    var categoryname: TextView = itemView.findViewById(R.id.categoryname)
    var image: ImageView = itemView.findViewById(R.id.image)

    val search: LinearLayout = itemView.findViewById(R.id.searchview)
    val relativeSubcategoryItems: RelativeLayout = itemView.findViewById(R.id.relative_category)


    init {
        try {

            relativeSubcategoryItems.setOnClickListener {
                listenerWeakReference.get()?.onClickListener(adapterPosition)

            }
            search.setOnClickListener {
                listenerWeakReference.get()?.onLongClickListener(adapterPosition)

            }
            itemView.setOnClickListener(this)
        }catch (x: Exception){
            x.printStackTrace()
        }
    }
}
