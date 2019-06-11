package com.dev.lishabora.Utils


interface OnCartItemEvent {
    fun delete(pos: Int)
    fun add(pos: Int)
    fun remove(pos: Int)


}
