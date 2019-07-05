package com.dev.dawaswift.adapters.delivery


import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnDeliveryViewItemClick
import com.dev.dawaswift.R
import com.google.android.material.switchmaterial.SwitchMaterial
import java.lang.ref.WeakReference


class AddressItemViewHolder(itemView: View, listener: OnDeliveryViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {


        //listenerWeakReference.get()?.onToDefault(adapterPosition,isChecked)

    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.edit) {
            listenerWeakReference.get()?.onEdit(adapterPosition)

        }

        if (v?.id == R.id.delete) {
            listenerWeakReference.get()?.onDelete(adapterPosition)

        }

        if (v?.id == R.id.switch_shipping) {
            listenerWeakReference.get()?.onToDefault(adapterPosition, true)

        }


    }

    private val listenerWeakReference: WeakReference<OnDeliveryViewItemClick> = WeakReference(listener)

    var itemVew: View
    var name: TextView
    var address: TextView
    var phone: TextView
    var edit: TextView
    var delete: TextView
    var switch: SwitchMaterial


    init {
        this.itemVew = itemView

        name = itemView.findViewById(R.id.name)
        address = itemView.findViewById(R.id.address)
        phone = itemView.findViewById(R.id.phone)
        edit = itemView.findViewById(R.id.edit)
        delete = itemView.findViewById(R.id.delete)
        switch = itemView.findViewById(R.id.switch_shipping)



        edit.setOnClickListener(this)
        delete.setOnClickListener(this)
        switch.setOnClickListener(this)

    }


}
