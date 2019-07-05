package com.dev.common.utils


interface OnDeliveryViewItemClick {
    fun onEdit(position: Int)
    fun onDelete(position: Int)
    fun onToDefault(position: Int, boolean: Boolean)


}
