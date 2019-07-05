package com.dev.dawaswift.adapters.delivery


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnDeliveryViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Address.Address

class DeliveryAdapter(
    private var type: Int,
    internal var main: Activity,
    private var modelList: List<Address>?,
    private val recyclerListener: OnDeliveryViewItemClick
) : RecyclerView.Adapter<AddressItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.address_item, parent, false)



        return AddressItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: AddressItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = model.name
        holder.address.text = model.description
        holder.phone.text = model.mobile


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


    fun refresh(modelList: List<Address>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
