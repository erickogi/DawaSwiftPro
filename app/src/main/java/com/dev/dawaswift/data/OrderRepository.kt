package com.dev.dawaswift.data

import CustomerRequestService
import NetworkUtils
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.data.local.AppDatabase
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.models.custom.Resource
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.common.utils.AgriException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Order.OrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(application: Application) {
    val ordersObservable = MutableLiveData<Resource<OrderResponse>>()

    private val db: AppDatabase
    private val profileDao: ProfileDao
    private val prefrenceManager: PrefrenceManager
    private val context: Context

    init {
        db = AppDatabase.getDatabase(application)!!
        profileDao = db.profileDao()
        prefrenceManager = PrefrenceManager(application.applicationContext)
        context = application.applicationContext
    }


    fun getCompletedOrders() {
        setIsLoading(Observable.ORDERS)
        if (NetworkUtils.isConnected(context)) {
            completedOrders()
        } else {
            setNetworkError(Observable.ORDERS)
        }
    }


    fun sanitizePhone(phone: String): String {
        if (phone == "") {
            return ""
        }

        if ((phone.length < 11) and phone.startsWith("0")) {
            return phone.replaceFirst("^0".toRegex(), "254")
        }
        //+254714406984
        return if (phone.length == 13 && phone.startsWith("+")) {
            phone.replaceFirst("^+".toRegex(), "")
        } else phone
    }


    private fun completedOrders() {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).completedOrders()
            call.enqueue(object : Callback<OrderResponse> {
                override fun onFailure(call: Call<OrderResponse>?, t: Throwable?) {
                    onFailure(Observable.ORDERS, t, FailureUtils().parseError(call, t))

                }

                override fun onResponse(call: Call<OrderResponse>?, response: Response<OrderResponse>?) {
                    onResponse(
                        response, Observable.ORDERS
                    )
                }
            })
        }
    }


    private fun setNetworkError(observable: Observable) {
        setIsError(
            observable, context.getString(R.string.network_issue_message),
            AgriException(context.getString(R.string.network_issue_message))
        )
    }

    private fun onFailure(observable: Observable, t: Throwable?, agriException: AgriException) {

        setIsError(observable, t.toString(), agriException)
    }

    private fun onResponse(response: Response<OrderResponse>?, observable: Observable) {

        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.statusCode!! > 0 && response.body()?.success!!) {
                    setIsSuccesful(observable, response.body()!!)


                } else {
                    response.body()?.statusMessage?.let {
                        setIsError(
                            observable, it,
                            AgriException(it, it, response.body()?.errors)
                        )
                    }
                }
            } else {
                setIsError(observable, "", ErrorUtils().parseError(response))
            }
        } else {
            setIsError(observable, "", AgriException("Error Loading Data"))
        }
    }

    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.ORDERS -> ordersObservable.postValue(Resource.loading(null))

        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.ORDERS -> ordersObservable.postValue(Resource.success(data as OrderResponse))

        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.ORDERS -> ordersObservable.postValue(Resource.error(message, null, exception))

        }
    }

    enum class Observable {
        ORDERS
    }

    fun saveProfile(data: Oauth) {
        profileDao.delete()
        data.profile?.let { profileDao.insertProfile(it) }
        prefrenceManager.saveProfile(data)
    }

    fun getProfile(): LiveData<Profile> {
        return profileDao.getProfile()
    }

    fun fetchProfile(): Profile {
        return profileDao.fetch()
    }
}