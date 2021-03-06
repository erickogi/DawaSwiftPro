/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 4:11 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 4:09 PM
 *
 */

import com.dev.common.models.NotificationsResponse
import com.dev.common.models.driver.balance.BalanceQuery
import com.dev.common.models.driver.balance.BalanceResponse
import com.dev.common.models.driver.requests.RequestActionResponse
import com.dev.common.models.driver.requests.RequestResponse
import com.dev.common.models.driver.requests.TripRequest
import com.dev.common.models.driver.trips.TripsResponse
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.models.location.LocationsResponse
import com.dev.common.models.oauth.ImageUploadResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author kogi
 */
interface EndPoints {


    /**VERIFY PHONE NUMBER EXISTENCE  **/
    @POST("oauth/verifyPhone.php")
    fun verifyPhone(@Body item: Profile): Call<Oauth>


    /**LOGIN WITH PHONE AND PASSWORD  **/
    @POST("oauth/verifyPassword.php")
    fun signIn(@Body item: Profile): Call<Oauth>

    /**LOGIN WITH PHONE AND PASSWORD  **/
    @POST("oauth/verifyEmail.php")
    fun verifyEmail(@Body item: Profile): Call<Oauth>


    /**REQUEST AN OTP CODE FOR PHONE NUMBER VERIFICATION  **/
    @POST("oauth/requestOTP.php")
    fun requestOtp(@Body item: Profile): Call<Oauth>


    /**CONFIRM AN OTP CODE FOR PHONE NUMBER VERIFICATION  **/
    @POST("oauth/verifyOTP.php")
    fun confirmOtp(@Body item: Profile): Call<Oauth>


    /**USED TO REGISTER A USER ( AFTER PHONE VERIFICATION)
     * FOR THIS USE CASE I AM JUST SENDING THE PHONE AND PASSWORD AND WILL UPDATE THE REST WITH THE NEXT API
     * **/
    @POST("accounts/create.php")
    fun createAccount(@Body item: Profile): Call<Oauth>

    /**
     * EDIT / UPDATE USER ACCOUNT
     * SEND (ENCRYPTED PASSWORD ALSO )
     * **/

    @POST("accounts/edit.php")
    fun updateAccount(@Body item: Profile): Call<Oauth>


    @POST("accounts/editPassword.php")
    fun updatePassword(@Body outh: Profile): Call<Oauth>


    @POST("counties.json")
    fun getCounties(@Body locationSearchModel: LocationSearchModel): Call<LocationsResponse>


    @POST("sub-counties.json")
    fun getSubCounties(@Body locationSearchModel: LocationSearchModel): Call<LocationsResponse>


    @POST("wards.json")
    fun getWards(@Body locationSearchModel: LocationSearchModel): Call<LocationsResponse>


    @GET("request/fetch.php")
    fun getRequests(): Call<RequestResponse>

    @GET("notification/fetch")
    fun getNotification(): Call<NotificationsResponse>




    @GET("trip/fetch.php")
    fun getTrips(): Call<TripsResponse>


    @POST("request/accept.php")
    fun acceptTrip(@Body id: TripRequest): Call<RequestActionResponse>


    @POST("request/reject.php")
    fun rejectTrip(@Body id: TripRequest): Call<RequestActionResponse>


    @GET("trip/current.php")
    fun currentTrip(): Call<RequestActionResponse>


    @POST("trip/begin.php")
    fun beginTrip(@Body id: TripRequest): Call<RequestActionResponse>

    @POST("trip/end.php")
    fun endTrip(@Body id: TripRequest): Call<RequestActionResponse>


    @POST("trip/balance.php")
    fun balances(@Body id: BalanceQuery): Call<BalanceResponse>

    @Multipart
    @POST("v3.php")
    fun upload(
        @Part("appId") appId: Int,
        @Part("file") re: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ImageUploadResponse>



}
