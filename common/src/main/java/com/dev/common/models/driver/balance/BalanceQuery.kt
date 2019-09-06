package com.dev.common.models.driver.balance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class BalanceQuery(
    @SerializedName("dateRange")
    @Expose var dateRange: DateRange?
)