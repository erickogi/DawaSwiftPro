/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

package com.dev.common.utils

import com.dev.common.utils.AgriException
import com.dev.common.utils.ErrorBody
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response


class ErrorUtils {

    fun parseError(response: Response<*>): AgriException {

        try {
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
            val erroBody: ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)




            return AgriException(erroBody?.message, erroBody?.status_name, erroBody?.errors)

        } catch (e: Exception) {

            return AgriException("Error Loading Data")

        }

    }
}