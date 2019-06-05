/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 6:30 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 6:30 PM
 *
 */

package com.dev.common.utils

import com.dev.common.utils.AgriException
import retrofit2.Call
import java.io.IOException


class FailureUtils {

    fun parseError(call: Call<*>?, t: Throwable?): AgriException {


        try {
            var ve: AgriException? = null
            if (call != null) {
                if (call.isCanceled) {
                    ve = AgriException("Canceled By User")
                } else {
                    if (t != null) {
                        if (t is IOException) {
                            ve = AgriException("Network failure. Please retry")

                        } else {
                            ve = AgriException(t.cause?.message)
                        }
                    }
                }
            } else {
                ve = AgriException(t?.message)
            }


            if (ve == null) {
                ve = AgriException(" Unknown Error ")
            }
            return ve
        } catch (ex: Exception) {
            return AgriException("Error Loading Data")

        }


    }
}