package com.dev.dawaswift.repository

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
import com.dev.common.R
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.models.Product.ProductResponse
import com.dev.dawaswift.models.Product.ProductSearchAndFilter
import com.dev.dawaswift.models.category.CategoriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val categoriesObservable = MutableLiveData<Resource<CategoriesResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getCategories() {
        setIsLoading(Observable.CATEGORIES)
        if (NetworkUtils.isConnected(context)) {
            executeCategories()
        } else {
            setNetworkError(Observable.CATEGORIES)
        }
    }



    private fun executeCategories( ) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token, "http://calista.co.ke/dawaswift_mock/").getCategories(fetchProfile())
            call.enqueue(object : Callback<CategoriesResponse> {
                override fun onFailure(call: Call<CategoriesResponse>?, t: Throwable?) {
                    onFailure(Observable.CATEGORIES, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<CategoriesResponse>?, response: Response<CategoriesResponse>?) {
                    onResponse(response, Observable.CATEGORIES)
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


            onResponseMulti(response as Response<CategoriesResponse>,observable)


    }
    private fun onResponseMulti(response: Response<CategoriesResponse>?, observable: Observable) {

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
            Observable.CATEGORIES -> categoriesObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.CATEGORIES -> categoriesObservable.postValue(Resource.success(data as CategoriesResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.CATEGORIES -> categoriesObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        CATEGORIES
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
