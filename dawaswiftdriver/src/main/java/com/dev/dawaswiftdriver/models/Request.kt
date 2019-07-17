package com.dev.dawaswiftdriver.models

import com.dev.dawaswiftdriver.TravelSerializable

import java.io.Serializable

class Request : Serializable {
    var travel: TravelSerializable
    var distance: Double? = null
    var fromDriver: Double? = null
    var cost: Double? = null

    constructor(travel: TravelSerializable, distance: Int, fromDriver: Int, cost: Double?) {
        this.travel = travel
        this.distance = distance.toDouble() / 1000
        this.fromDriver = fromDriver.toDouble() / 1000
        this.cost = cost
    }

    constructor(travel: TravelSerializable, distance: Double?, fromDriver: Double?, cost: Double?) {
        this.travel = travel
        this.distance = distance
        this.fromDriver = fromDriver
        this.cost = cost
    }
}
