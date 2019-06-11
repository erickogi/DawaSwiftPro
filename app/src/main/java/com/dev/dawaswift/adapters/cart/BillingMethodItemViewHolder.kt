package com.dev.agenda.Adapters


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import java.lang.ref.WeakReference


class BillingMethodItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var image: ImageView
    var detail: TextView
    var name: TextView


    init {
        this.itemVew = itemView

        image = itemView.findViewById(R.id.image)
        detail = itemView.findViewById(R.id.detail)
        name = itemView.findViewById(R.id.name)

        itemView.setOnClickListener(this)

    }


}
