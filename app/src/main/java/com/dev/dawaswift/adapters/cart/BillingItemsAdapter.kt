package com.kogicodes.sokoni.adapters.V1


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.dawaswift.adapters.cart.BillingItemViewHolder
import com.dev.lishabora.Utils.OnAddressItemEvent
import com.kogicodes.sokoni.Config
import com.kogicodes.sokoni.R
import com.kogicodes.sokoni.models.v1.Billing.Billing

class BillingItemsAdapter(internal var main: Activity,

                          private val context: Context,
                          private var modelList: List<Billing>?,
                          private val recyclerListener: OnAddressItemEvent) : RecyclerView.Adapter<BillingItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.billing_item, parent, false)



        return BillingItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: BillingItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.detail.text = model.detail
        holder.name.text = model.name
        holder.radio.isChecked = model._default!!
        Glide.with(context).load(Config.app_base_image + "" + model.image).into(holder.image)


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<Billing>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
