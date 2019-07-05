package com.dev.dawaswift.repository

import CustomerRequestService
import NetworkUtils
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.R
import com.dev.common.data.local.AppDatabase
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.models.custom.Resource
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.common.utils.AgriException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.dev.dawaswift.models.category.HealthAreasResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HealthAreaRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val healthAreascategoriesObservable = MutableLiveData<Resource<HealthAreasResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getHealthAreas() {
        setIsLoading(Observable.HEALTHAREAS)
        if (NetworkUtils.isConnected(context)) {
            executeHealtAreas()
        } else {
            setNetworkError(Observable.HEALTHAREAS)
        }
    }



    private fun executeHealtAreas( ) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).getHealthAreas()
            call.enqueue(object : Callback<HealthAreasResponse> {
                override fun onFailure(call: Call<HealthAreasResponse>?, t: Throwable?) {
                    onFailure(Observable.HEALTHAREAS, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<HealthAreasResponse>?, response: Response<HealthAreasResponse>?) {
                    onResponse(response, Observable.HEALTHAREAS)
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
    private  fun <T>  onResponse (response: Response<T>?,observable: Observable){


            onResponseMulti(response as Response<HealthAreasResponse>,observable)


    }
    private fun onResponseMulti(response: Response<HealthAreasResponse>?, observable: Observable) {

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
            Observable.HEALTHAREAS -> healthAreascategoriesObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.HEALTHAREAS -> healthAreascategoriesObservable.postValue(Resource.success(data as HealthAreasResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.HEALTHAREAS -> healthAreascategoriesObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        HEALTHAREAS
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

        var p = profileDao.fetch()

        if (p == null) {
            p = Profile()
        }
        if (p.token == null) {
            p.token = ""
        }
        return p
    }
}
