package com.dev.dawaswiftdriver.interfaces

import com.dev.common.models.driver.requests.TripRequest


interface RequestAction {
    fun accept(request: TripRequest)
    fun reject(request: TripRequest)

}