package com.dev.dawaswift.adapters.cart


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.dawaswift.R
import com.dev.lishabora.Utils.OnCartItemEvent
import java.lang.ref.WeakReference

class CartItemViewHolder(type: Int, itemView: View, listener: OnCartItemEvent) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v == add)
            listenerWeakReference.get()?.add(adapterPosition)
        if (v == remove)
            listenerWeakReference.get()?.remove(adapterPosition)
        if (v == delete)
            listenerWeakReference.get()?.delete(adapterPosition)
    }

    private val listenerWeakReference: WeakReference<OnCartItemEvent> = WeakReference(listener)

    var itemVew: View
    var name: TextView
    var quantity: TextView
    var price: TextView
    var image: ImageView
    lateinit var add: ImageView
    lateinit var remove: ImageView
    lateinit var delete: ImageView


    init {
        this.itemVew = itemView

        name = itemView.findViewById(R.id.title)
        price = itemView.findViewById(R.id.price)
        quantity = itemView.findViewById(R.id.quantity)
        image = itemView.findViewById(R.id.image)

        if (type == 0) {
            add = itemView.findViewById(R.id.add)
            remove = itemView.findViewById(R.id.remove)
            delete = itemView.findViewById(R.id.delete)

            add.setOnClickListener(this)
            remove.setOnClickListener(this)
            delete.setOnClickListener(this)
        }



    }


}
