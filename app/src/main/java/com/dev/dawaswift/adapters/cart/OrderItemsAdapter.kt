package com.dev.dawaswift.adapters.cart


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agriclinic.common.adapters.cart.CartItemViewHolder
import com.dev.common.utils.CommonUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Order.OrderItem
import com.dev.lishabora.Utils.OnCartItemEvent

class OrderItemsAdapter(
    private val type: Int, internal var main: Activity,

    private val context: Context,
    private var modelList: List<OrderItem>?,
    private val recyclerListener: OnCartItemEvent
) : RecyclerView.Adapter<CartItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)




        if (type == 0) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        } else {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_product_item, parent, false)

        }

        return CartItemViewHolder(type, itemView!!, recyclerListener)


    }


    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = "Item  : " + model.productModel?.name
        holder.price.text = "Price : " + model.productModel?.discountedPrice

        if (type == 0) {
            holder.quantity.text = "" + model.quantity
        } else {
            holder.quantity.text = "Quantity :" + model.quantity

        }
        CommonUtils().loadImageUrl(context, model.productModel?.image, holder.image)


    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<OrderItem>) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
