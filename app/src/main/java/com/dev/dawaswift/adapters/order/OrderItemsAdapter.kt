package com.dev.dawaswift.adapters.order


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Order.OrderItems

class OrderItemsAdapter(
    internal var main: Activity,
    private var modelList: List<OrderItems>?,
    private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<OrderItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)



        return OrderItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.code.text = model.docNo.toString()



        holder.status.text = model.statusName
        holder.quantity.text = " " + model.itemsCount
        holder.value.text = "Ksh " + model.totalAmount


        CommonUtils().displayFormattedDate(model.createdOn!!, holder.day, holder.month, holder.year)
    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<OrderItems>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
