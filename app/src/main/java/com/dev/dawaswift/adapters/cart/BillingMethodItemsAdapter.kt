package com.kogicodes.sokoni.adapters.V1


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.agenda.Adapters.BillingMethodItemViewHolder
import com.dev.lishabora.Utils.OnRecyclerViewItemClick
import com.kogicodes.sokoni.Config
import com.kogicodes.sokoni.R
import com.kogicodes.sokoni.models.v1.Billing.BillingMethod

class BillingMethodItemsAdapter(internal var main: Activity,

                                private val context: Context,
                                private var modelList: List<BillingMethod>?,
                                private val recyclerListener: OnRecyclerViewItemClick) : RecyclerView.Adapter<BillingMethodItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingMethodItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.billing_method_item, parent, false)



        return BillingMethodItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: BillingMethodItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.detail.text = model.detail
        holder.name.text = model.name
        Glide.with(context).load(Config.app_base_image + "" + model.image).into(holder.image)


        if (model.isActive == 0) {
            holder.itemVew.isClickable = false
            holder.itemVew.alpha = 0.33f
            holder.name.isEnabled = false
            holder.detail.isEnabled = false
        } else {
            holder.itemVew.isClickable = true
            holder.itemVew.alpha = 1f
            holder.name.isEnabled = true
            holder.detail.isEnabled = true
        }
    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<BillingMethod>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
