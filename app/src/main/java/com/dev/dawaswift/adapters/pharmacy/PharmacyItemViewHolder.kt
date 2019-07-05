package com.dev.dawaswift.adapters.pharmacy


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import java.lang.ref.WeakReference


class PharmacyItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)


    var name: TextView = itemView.findViewById(R.id.name)
    var email: TextView = itemView.findViewById(R.id.email)
    var phone: TextView = itemView.findViewById(R.id.phone)
    var closed: TextView = itemView.findViewById(R.id.closed)
    var open: TextView = itemView.findViewById(R.id.open)
    var image: ImageView = itemView.findViewById(R.id.image)


    init {
        itemView.setOnClickListener(this)
    }


}
