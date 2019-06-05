/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

package com.dev.common.utils

class AgriException : Exception {

    var errorType: String? = null
    var errorMessage: String? = null
    var errors: List<String>? = null

    constructor (errorMessage: String?) : super(errorMessage) {
        this.errorMessage = errorMessage

    }


    constructor(errorType: String?, errorMessage: String?) : super(errorMessage) {

        this.errorType = errorType
        this.errorMessage = errorMessage
    }

    constructor(errorType: String?, errorMessage: String?, errors: List<String>?) : super(errorMessage) {

        this.errorType = errorType
        this.errorMessage = errorMessage
        this.errors = errors
    }


}