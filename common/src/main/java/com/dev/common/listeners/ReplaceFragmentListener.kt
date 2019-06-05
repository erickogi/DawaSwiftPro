package com.dev.common.listeners

import com.dev.common.data.FRAGMENTS_NAV_KEYS


interface ReplaceFragmentListener {
    fun <T> replace(currentFragment: FRAGMENTS_NAV_KEYS, nextFragment: FRAGMENTS_NAV_KEYS, data: T?)
    fun <T> toActivity(currentFragment: FRAGMENTS_NAV_KEYS, data: T?, finishPrevious: Boolean)

}