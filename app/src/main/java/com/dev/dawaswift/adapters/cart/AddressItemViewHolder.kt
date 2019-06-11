package com.dev.agenda.Adapters


import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.lishabora.Utils.OnAddressItemEvent
import com.kogicodes.sokoni.R
import java.lang.ref.WeakReference


class AddressItemViewHolder(itemView: View, listener: OnAddressItemEvent) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v == edit)
            listenerWeakReference.get()?.edit(adapterPosition)
        if (v == delete)
            listenerWeakReference.get()?.delete(adapterPosition)
        if (v == radio)
            listenerWeakReference.get()?.radioClick(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnAddressItemEvent> = WeakReference(listener)

    var itemVew: View
    var name: TextView
    var phone: TextView
    var location: TextView
    var edit: TextView
    var delete: TextView
    var radio: RadioButton


    init {
        this.itemVew = itemView

        name = itemView.findViewById(R.id.name)
        phone = itemView.findViewById(R.id.phone)
        location = itemView.findViewById(R.id.location)
        edit = itemView.findViewById(R.id.edit)
        delete = itemView.findViewById(R.id.delete)
        radio = itemView.findViewById(R.id.radioUse)

        edit.setOnClickListener(this)
        delete.setOnClickListener(this)
        radio.setOnClickListener(this)


    }


}
