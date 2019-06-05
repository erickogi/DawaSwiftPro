/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.dev.dawaswift.adapters.healtharea


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.category.HealthAreaItemHolder
import com.dev.dawaswift.models.category.HealthArea

class HealthAreaAdapter(
    private var context: Context,
    private var modelList: List<HealthArea>?,
    val lst: OnRecyclerViewItemClick
) : RecyclerView.Adapter<HealthAreaItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAreaItemHolder {
        val itemView: View? =
            LayoutInflater.from(parent.context).inflate(R.layout.health_area_item, parent, false)



        return HealthAreaItemHolder(itemView!!, lst)
    }


    override fun onBindViewHolder(holder: HealthAreaItemHolder, position: Int) {

        val model = modelList!![position]
        holder.categoryname.text = model.name?.toLowerCase()?.capitalize()
        CommonUtils().loadImage(context, model.image, holder.image)


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun updateList(modelLists: List<HealthArea>?) {

        this.modelList = modelLists
        notifyDataSetChanged()

    }


}