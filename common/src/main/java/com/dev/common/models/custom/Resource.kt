package com.dev.common.models.custom

import com.dev.common.utils.AgriException

class Resource<T>
private constructor(val status: Status, val data: T?, val message: String?, val exception: AgriException?) {
    companion object {


        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(msg: String, data: T?, exception: AgriException): Resource<T> {
            return Resource(Status.ERROR, data, msg, exception)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}