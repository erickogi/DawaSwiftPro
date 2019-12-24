package com.dev.dawaswift.adapters.delivery


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnDeliveryViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Address.PickUpPoints

class PickUpPointsAdapter(
    private var type: Int,
    internal var main: Activity,
    private var modelList: List<PickUpPoints>?,
    private val recyclerListener: OnDeliveryViewItemClick
) : RecyclerView.Adapter<PickUpItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickUpItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.pickup_point, parent, false)



        return PickUpItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: PickUpItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = model.name
        holder.address.text = model.address


        if (type == 1) {
            holder.switch.visibility = View.VISIBLE
        } else {
            holder.switch.visibility = View.GONE

        }
        holder.switch.isChecked = model.isDefault!!
    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<PickUpPoints>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
