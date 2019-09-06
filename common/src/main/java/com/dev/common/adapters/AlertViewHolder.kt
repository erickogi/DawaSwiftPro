package com.dev.common.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.models.Notification
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import kotlinx.android.synthetic.main.notification_item.view.*
import java.lang.ref.WeakReference

class AlertViewHolder(private val view: View, onclick: OnRecyclerViewItemClick) : RecyclerView.ViewHolder(view),
    GenericAdapter.Binder<Notification> {


    private val listenerWeakReference: WeakReference<OnRecyclerViewItemClick> = WeakReference(onclick)

    override fun bind(data: Notification) {

        view.title.text = data.title
        view.message.text = data.description
        // view.message.text = data.description

        CommonUtils().displayFormattedDate(data.createdOn!!, view.day, view.month, view.year)

    }
}