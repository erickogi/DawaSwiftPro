package com.kogicodes.sokoni.adapters


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.product.DetailItemViewHolder

class DetailItemsAdapter(internal var main: Activity,

                         private val context: Context,
                         private var modelList: List<String>?,
                         private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<DetailItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.details_item, parent, false)



        return DetailItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.value.text = model

    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<String>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
