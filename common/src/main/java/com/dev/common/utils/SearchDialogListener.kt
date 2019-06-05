package com.dev.common.utils

import com.dev.common.models.custom.SearchModel


interface SearchDialogListener<T> {
    fun onResults(item: SearchModel<T>?, position: Int)


}
