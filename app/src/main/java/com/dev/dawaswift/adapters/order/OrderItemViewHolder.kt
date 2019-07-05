package com.dev.dawaswift.adapters.order


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import java.lang.ref.WeakReference


class OrderItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var code: TextView
    var day: TextView
    var month: TextView
    var year: TextView
    var quantity: TextView


    var value: TextView
    var status: TextView
    // var paymentStatus: TextView


    init {
        this.itemVew = itemView

        code = itemView.findViewById(R.id.code)
        day = itemView.findViewById(R.id.day)
        month = itemView.findViewById(R.id.month)
        year = itemView.findViewById(R.id.year)
        quantity = itemView.findViewById(R.id.quantity)
        value = itemView.findViewById(R.id.value)
        status = itemView.findViewById(R.id.status)
        //  paymentStatus = itemView.findViewById(R.id.payment_status)

        itemView.setOnClickListener(this)

    }


}
