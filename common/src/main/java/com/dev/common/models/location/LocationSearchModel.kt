package com.dev.common.models.location

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationSearchModel {
    constructor(countyId: Int? = 0, subountyId: Int? = 0) {
        this.subountyId = subountyId
        this.countyId = countyId
    }

    constructor(
        country: String?,
        city: String?,
        state: String?,
        postalCode: String?,
        address: String?,
        knownName: String?
    ) {
        this.country = country
        this.city = city
        this.state = state
        this.postalCode = postalCode
        this.address = address
        this.knownName = knownName
    }


    @SerializedName("countryId")
    @Expose
    var countryId: Int? = null


    @SerializedName("countyId")
    @Expose
    var countyId: Int? = null


    @SerializedName("SubCountyId")
    @Expose
    var subountyId: Int? = null


    @SerializedName("wardId")
    @Expose
    var wardId: Int? = null


    @SerializedName("constituencyId")
    @Expose
    var constituencyId: Int? = null


    var country: String? = null
    var city: String? = null
    var state: String? = null
    var postalCode: String? = null
    var address: String? = null
    var knownName: String? = null

    var lat: String? = null
    var lon: String? = null




}