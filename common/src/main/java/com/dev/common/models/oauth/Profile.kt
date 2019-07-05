package com.dev.common.models.oauth

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    indices = [(Index("id"))],
    primaryKeys = ["id"]
)
@JvmSuppressWildcards
class Profile : Serializable {

    @field:SerializedName("job_id")
    var jobId: String? = null

    @field:SerializedName("code")

    var code: String? = null

    @field:SerializedName("createdAt")

    var createdAt: String? = null
    @field:SerializedName("updatedAt")

    var updatedAt: String? = null

    @field:SerializedName("otpCode")

    var otpCode: String? = null


    @field:SerializedName("dob")

    var dob: String? = null


    @field:SerializedName("sex")

    var sex: String? = null


    @field:SerializedName("county")

    var county: String? = null


    @field:SerializedName("subCounty")

    var subCounty: String? = null

    @field:SerializedName("ward")

    var ward: String? = null


    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("firstName")
    @Expose
    var firstName: String? = null
    @SerializedName("lastName")
    @Expose
    var lastName: String? = null
    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("firebaseToken")
    @Expose
    var firebaseToken: String? = null
    @SerializedName("countyId")
    @Expose
    var countyId: String? = null
    @SerializedName("subCountyId")
    @Expose
    var subCountyId: String? = null
    @SerializedName("wardId")
    @Expose
    var wardId: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("roleId")
    @Expose
    var roleId: Int? = null

    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null


    var accountAction: Int? = null
    var avatarUri: Uri? = null


    @SerializedName("lat")
    @Expose
    var lat: String? = null


    @SerializedName("lon")
    @Expose
    var lon: String? = null



    @Ignore
    constructor(firstName: String?, lastName: String?, email: String?, mobile: String?, password: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.mobile = mobile
        this.password = password
    }

    @Ignore
    constructor(firstName: String?, email: String?, password: String?) {
        this.firstName = firstName
        this.lastName = ""
        this.email = email
        this.mobile = ""
        this.password = password
    }

//    @Ignore constructor( email: String?,  phone: String?) {
//        this.mobile = mobile
//
//        this.email = email
//    }


    @Ignore
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


    constructor()

    @Ignore
    constructor(email: String?) {
        this.email = email
    }


}