package com.dev.common.data.repository

import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.data.local.AppDatabase
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.models.custom.Resource
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.models.location.LocationsResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.common.utils.AgriException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.dev.common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationRepository(application: Application) {
    private val profileDao: ProfileDao

    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val countiesObservable = MutableLiveData<Resource<LocationsResponse>>()
    val subContiesObservable = MutableLiveData<Resource<LocationsResponse>>()
    val wardsObservable = MutableLiveData<Resource<LocationsResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getCounties(parameters: LocationSearchModel) {
        setIsLoading(Observable.COUNTIES)
        if (NetworkUtils.isConnected(context)) {
            executeCounties(parameters)
        } else {
            setNetworkError(Observable.COUNTIES)
        }
    }

    fun getSubCounties(parameters: LocationSearchModel) {
        setIsLoading(Observable.SUB_COUNTIES)
        if (NetworkUtils.isConnected(context)) {
            executeSubCounties(parameters)
        } else {
            setNetworkError(Observable.SUB_COUNTIES)
        }
    }

    fun getWards(parameters: LocationSearchModel) {
        setIsLoading(Observable.WARDS)
        if (NetworkUtils.isConnected(context)) {
            executeWards(parameters)
        } else {
            setNetworkError(Observable.WARDS)
        }
    }


    private fun executeCounties(locationSearchModel: LocationSearchModel) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token, "").getCounties(locationSearchModel)
            call.enqueue(object : Callback<LocationsResponse> {
                override fun onFailure(call: Call<LocationsResponse>?, t: Throwable?) {
                    onFailure(Observable.COUNTIES, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<LocationsResponse>?, response: Response<LocationsResponse>?) {
                    onResponse(response, Observable.COUNTIES)
                }
            })
        }
    }

    private fun executeSubCounties(locationSearchModel: LocationSearchModel) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token, "").getSubCounties(locationSearchModel)
            call.enqueue(object : Callback<LocationsResponse> {
                override fun onFailure(call: Call<LocationsResponse>?, t: Throwable?) {
                    onFailure(Observable.SUB_COUNTIES, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<LocationsResponse>?, response: Response<LocationsResponse>?) {
                    onResponse(response, Observable.SUB_COUNTIES)
                }
            })
        }
    }

    private fun executeWards(locationSearchModel: LocationSearchModel) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token, "").getWards(locationSearchModel)
            call.enqueue(object : Callback<LocationsResponse> {
                override fun onFailure(call: Call<LocationsResponse>?, t: Throwable?) {
                    onFailure(Observable.WARDS, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<LocationsResponse>?, response: Response<LocationsResponse>?) {
                    onResponse(response, Observable.WARDS)
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

    private fun onResponse(response: Response<LocationsResponse>?, observable: Observable) {

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
            Observable.COUNTIES -> countiesObservable.postValue(Resource.loading(null))
            Observable.SUB_COUNTIES -> subContiesObservable.postValue(Resource.loading(null))
            Observable.WARDS -> wardsObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.COUNTIES -> countiesObservable.postValue(Resource.success(data as LocationsResponse))
            Observable.SUB_COUNTIES -> subContiesObservable.postValue(Resource.success(data as LocationsResponse))
            Observable.WARDS -> wardsObservable.postValue(Resource.success(data as LocationsResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.COUNTIES -> countiesObservable.postValue(Resource.error(message, null, exception))
            Observable.SUB_COUNTIES -> subContiesObservable.postValue(Resource.error(message, null, exception))
            Observable.WARDS -> wardsObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        COUNTIES,
        SUB_COUNTIES,
        WARDS
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
