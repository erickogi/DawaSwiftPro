package com.dev.dawaswift.adapters.chats


import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import java.lang.ref.WeakReference


class ChatThreadViewHolder(view: Int, itemView: View, listener: OnRecyclerViewItemClick) :
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener, View.OnLongClickListener {
    override fun onLongClick(v: View?): Boolean {
        listenerWeakReference.get()?.onLongClickListener(adapterPosition)
        return true
    }

    override fun onClick(v: View?) {

        listenerWeakReference.get()?.onClickListener(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(listener)

    var itemVew: View
    var name: TextView
    var date: TextView
    var message: TextView
    var image: ImageView
    var product: TextView
    var linearProduct: LinearLayout


    init {
        this.itemVew = itemView

        if (view == 22) {
            name = itemView.findViewById(R.id.name)
            date = itemView.findViewById(R.id.date)
            message = itemView.findViewById(R.id.message)
            image = itemView.findViewById(R.id.image)
            product = itemView.findViewById(R.id.product_id)
            linearProduct = itemView.findViewById(R.id.product_view)

        } else {
            name = itemView.findViewById(R.id.MEname)
            date = itemView.findViewById(R.id.MEdate)
            message = itemView.findViewById(R.id.MEmessage)
            image = itemView.findViewById(R.id.MEimage)
            product = itemView.findViewById(R.id.MEproduct_id)
            linearProduct = itemView.findViewById(R.id.MEproduct_view)

        }


        linearProduct.setOnClickListener(this)
        image.setOnLongClickListener(this)

    }


}
