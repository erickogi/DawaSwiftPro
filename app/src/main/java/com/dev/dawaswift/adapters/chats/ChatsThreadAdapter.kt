package com.dev.dawaswift.adapters.chats


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.chat.Message

class ChatsThreadAdapter(
    var myId: String,
    internal var main: Context,
    private var modelList: List<Message>?,
    private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<ChatThreadViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatThreadViewHolder {
        var itemView: View? = null

        if (viewType == 22) {

            itemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_message_friend, parent, false)
            return ChatThreadViewHolder(22, itemView!!, recyclerListener)
        } else {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_message_user, parent, false)
            return ChatThreadViewHolder(21, itemView!!, recyclerListener)

        }

    }


    override fun onBindViewHolder(holder: ChatThreadViewHolder, position: Int) {

        val model = modelList!![position]

        var name = model.pharmacyName
        if (model.sender == myId) {
            name = "Me"
        }



        holder.name.text = name
        holder.message.text = model.message
        holder.date.text = model.createdOn

        if (model.sender == myId) {
            holder.linearProduct.visibility = View.GONE

            if (model.image != null && model.image != "") {
                holder.image.visibility = View.VISIBLE
                CommonUtils().loadImage(main, model.image, holder.image)

            } else {
                holder.image.visibility = View.GONE

            }
        } else {
            holder.image.visibility = View.GONE

            if (model.hasResource == "1") {
                holder.linearProduct.visibility = View.VISIBLE
                holder.product.text = model.resourceId

            } else {
                holder.linearProduct.visibility = View.GONE

            }


        }

    }

    override fun getItemViewType(position: Int): Int {


        return if (modelList?.get(position)?.sender.equals(myId)) {
            21
        } else {
            22
        }
    }

    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<Message>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
