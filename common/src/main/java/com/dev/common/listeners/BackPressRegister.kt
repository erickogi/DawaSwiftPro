package com.dev.common.listeners

import com.dev.common.listeners.BackPressHandler


interface BackPressRegister {
    fun registerHandler(handler: BackPressHandler)
    fun unregisterHandler(handler: BackPressHandler)
}