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
import com.dev.dawaswift.models.category.SubCategory

class SubCategoryAdapter(
    private var resourceId: Int,
    private var context: Context,
    private var modelList: List<SubCategory>?,
    val lst: OnRecyclerViewItemClick
) : RecyclerView.Adapter<SubCategoryItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryItemHolder {





            return SubCategoryItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.subcategory, parent, false)!!, lst)


    }


    override fun onBindViewHolder(holder: SubCategoryItemHolder, position: Int) {

        val model = modelList!![position]
        holder.categoryname.text = model.name?.toLowerCase()?.capitalize()
        var t=model.name?.first().toString().capitalize()
        holder.txtT.text=t


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun updateList(modelLists: List<SubCategory>?) {

        this.modelList = modelLists
        notifyDataSetChanged()

    }


}