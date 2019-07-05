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
import com.dev.dawaswift.models.Address.Address
import com.dev.dawaswift.models.Address.AddressResponse
import com.dev.dawaswift.models.Address.AddressesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val deliveriesObservable = MutableLiveData<Resource<AddressesResponse>>()
    val deliveriesActionObservable = MutableLiveData<Resource<AddressResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getAddress() {
        setIsLoading(Observable.FETCH)
        if (NetworkUtils.isConnected(context)) {
            executeAddress()
        } else {
            setNetworkError(Observable.FETCH)
        }
    }


    fun createAddress(addItem: Address) {
        setIsLoading(Observable.CREATE)
        if (NetworkUtils.isConnected(context)) {
            executeCreate(addItem)
        } else {
            setNetworkError(Observable.CREATE)
        }
    }


    fun editAddress(addItem: Address) {
        setIsLoading(Observable.EDIT)
        if (NetworkUtils.isConnected(context)) {
            executeEdit(addItem)
        } else {
            setNetworkError(Observable.EDIT)
        }
    }


    fun deleteAddress(addItem: Address) {
        setIsLoading(Observable.FETCH)
        if (NetworkUtils.isConnected(context)) {
            executeDelete(addItem)
        } else {
            setNetworkError(Observable.FETCH)
        }
    }


    private fun executeAddress() {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).deliveriesAddress()
            call.enqueue(object : Callback<AddressesResponse> {
                override fun onFailure(call: Call<AddressesResponse>?, t: Throwable?) {
                    onFailure(Observable.FETCH, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<AddressesResponse>?, response: Response<AddressesResponse>?) {

                    onResponses(response, Observable.FETCH)
                }
            })
        }
    }

    private fun executeCreate(addItem: Address) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).createDelivery(addItem)
            call.enqueue(object : Callback<AddressesResponse> {
                override fun onFailure(call: Call<AddressesResponse>?, t: Throwable?) {
                    onFailure(Observable.CREATE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<AddressesResponse>?, response: Response<AddressesResponse>?) {
                    onResponses(response, Observable.CREATE)
                }
            })
        }
    }

    private fun executeEdit(addItem: Address) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).editDelivery(addItem)
            call.enqueue(object : Callback<AddressResponse> {
                override fun onFailure(call: Call<AddressResponse>?, t: Throwable?) {
                    onFailure(Observable.EDIT, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<AddressResponse>?, response: Response<AddressResponse>?) {
                    onResponse(response, Observable.EDIT)
                }
            })
        }
    }

    private fun executeDelete(addItem: Address) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).deleteDelivery(addItem)
            call.enqueue(object : Callback<AddressesResponse> {
                override fun onFailure(call: Call<AddressesResponse>?, t: Throwable?) {
                    onFailure(Observable.DELETE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<AddressesResponse>?, response: Response<AddressesResponse>?) {
                    onResponses(response, Observable.DELETE)
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


    private fun onResponse(response: Response<AddressResponse>?, observable: Observable) {

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

    private fun onResponses(response: Response<AddressesResponse>?, observable: Observable) {

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
        deliveriesObservable.postValue(Resource.loading(null))
        // deliveriesActionObservable.postValue(Resource.loading(null))

    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        deliveriesObservable.postValue(Resource.success(data as AddressesResponse))
        // deliveriesActionObservable.postValue(Resource.success(data as AddressResponse))


    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        deliveriesObservable.postValue(Resource.error(message, null, exception))
        //  deliveriesActionObservable.postValue(Resource.error(message, null, exception))

    }

    enum class Observable {
        FETCH, CREATE, DELETE, EDIT
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