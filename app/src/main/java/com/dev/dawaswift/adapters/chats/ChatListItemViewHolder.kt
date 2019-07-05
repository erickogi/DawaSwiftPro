package com.dev.dawaswift.adapters.chats


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import java.lang.ref.WeakReference


class ChatListItemViewHolder(itemView: View, listener: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var name: TextView
    var day: TextView
    var month: TextView
    var year: TextView
    var message: TextView


    var image: ImageView
    var done: ImageView
    var doneAll: ImageView
    // var paymentStatus: TextView


    init {
        this.itemVew = itemView

        name = itemView.findViewById(R.id.name)
        day = itemView.findViewById(R.id.day)
        month = itemView.findViewById(R.id.month)
        year = itemView.findViewById(R.id.year)
        message = itemView.findViewById(R.id.message)
        image = itemView.findViewById(R.id.image)
        done = itemView.findViewById(R.id.done)
        doneAll = itemView.findViewById(R.id.done_all)
        //  paymentStatus = itemView.findViewById(R.id.payment_status)

        itemView.setOnClickListener(this)

    }


}
