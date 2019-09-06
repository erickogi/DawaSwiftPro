package com.dev.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.R
import com.dev.common.models.Notification
import com.dev.common.utils.OnRecyclerViewItemClick

class GenericAdapter<T>(
    private val context: Context,
    private val items: List<T>,
    val listener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when {
            items.all { it is Notification } -> AlertViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.notification_item,
                    parent,
                    false
                ), listener
            )
            else -> AlertViewHolder(
                LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false),
                listener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    internal interface Binder<T> {
        fun bind(data: T)
    }
}