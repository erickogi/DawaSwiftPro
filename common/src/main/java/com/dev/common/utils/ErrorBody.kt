/*
 * *
 *  * Created by Kogi Eric  on 5/20/19 5:42 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/20/19 3:58 PM
 *
 */

package com.dev.common.utils

internal class ErrorBody {

    var status_code: Int? = 0
    var status_name: String? = "Error"
    var success: Boolean? = false
    var message: String? = "Error"
    var result_code: Int? = 0
    var errors: List<String>? = null

}