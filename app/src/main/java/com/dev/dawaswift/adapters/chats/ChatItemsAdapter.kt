package com.dev.dawaswift.adapters.chats


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.chat.MessageList

class ChatItemsAdapter(
    internal var main: Context,
    private var modelList: List<MessageList>?,
    private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<ChatListItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)



        return ChatListItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: ChatListItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = model.message?.pharmacyName.toString()




        holder.message.text = model.message?.message

        CommonUtils().loadImage(main, model.message?.pharmacyImage, holder.image)

        CommonUtils().displayFormattedDate(model.message?.createdOn!!, holder.day, holder.month, holder.year)

        if (model.message?.isRead == "1") {
            holder.doneAll.visibility = View.VISIBLE
            holder.done.visibility = View.GONE
        } else {
            holder.doneAll.visibility = View.GONE
            holder.done.visibility = View.VISIBLE
        }
    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<MessageList>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
