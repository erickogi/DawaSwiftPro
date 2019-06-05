/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.dev.dawaswift.adapters.category


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.data.Constants
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.category.Category
import com.dev.dawaswift.models.category.HealthArea

class CategoryAdapter(
    private var resourceId: Int,
    private var context: Context,
    private var modelList: List<Category>?,
    val lst: OnRecyclerViewItemClick
) : RecyclerView.Adapter<CategoryItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemHolder {




        if(resourceId==Constants().HORIZONTAL) {
            return CategoryItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false), lst)
        }else{
            return CategoryItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item_linear, parent, false)!!, lst)

        }
    }


    override fun onBindViewHolder(holder: CategoryItemHolder, position: Int) {

        val model = modelList!![position]
        holder.categoryname.text = model.name?.toLowerCase()?.capitalize()
        CommonUtils().loadImage(context,  model.image, holder.image)


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun updateList(modelLists: List<Category>?) {

        this.modelList = modelLists
        notifyDataSetChanged()

    }


}