package com.dev.common.models.driver.balance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DateRange {
    @SerializedName("start")
    @Expose
    var start: String? = null
    @SerializedName("end")
    @Expose
    var end: String? = null

    constructor(start: String?, end: String?) {
        this.start = start
        this.end = end
    }
}