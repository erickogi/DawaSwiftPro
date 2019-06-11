package com.kogicodes.sokoni.adapters.V1


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.agenda.Adapters.AddressItemViewHolder
import com.dev.lishabora.Utils.OnAddressItemEvent
import com.kogicodes.sokoni.R
import com.kogicodes.sokoni.models.v1.Address.Address

class AddressItemsAdapter(internal var main: Activity,

                          private val context: Context,
                          private var modelList: List<Address>?,
                          private val recyclerListener: OnAddressItemEvent) : RecyclerView.Adapter<AddressItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.address_item, parent, false)



        return AddressItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: AddressItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = model.name
        holder.phone.text = "" + model.phone
        holder.location.text = "" + model.country + " -> " + model.state + " -> " + model.town
        holder.radio.isChecked = model._default!!


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<Address>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
