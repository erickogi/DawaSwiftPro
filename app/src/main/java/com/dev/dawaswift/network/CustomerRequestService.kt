/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

import RequestService.getRetrofit
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kogi
 */
object CustomerRequestService {
    var gson = GsonBuilder()
        .setLenient()
        .create()





    fun getService(token: String?, baseUrl: String): CustomerEndPoints {
        var baseUrll=BaseUrls().MOCKDAWASIFT
        if(baseUrl==null||baseUrl==""){
            baseUrll=baseUrl
        }
        if (token == null) {
            return getRetrofit("", baseUrll).create(CustomerEndPoints::class.java)

        } else {
            return getRetrofit(token, baseUrll).create(CustomerEndPoints::class.java)

        }
    }


    fun getService(consumerKey: String, secretKey: String, baseUrl: String): EndPoints {
        return getRetrofit(baseUrl, consumerKey, secretKey).create(EndPoints::class.java)
    }


}
