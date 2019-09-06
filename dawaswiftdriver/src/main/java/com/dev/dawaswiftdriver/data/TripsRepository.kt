package com.dev.dawaswiftdriver.data

import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.R
import com.dev.common.data.local.AppDatabase
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.models.custom.Resource
import com.dev.common.models.driver.balance.BalanceQuery
import com.dev.common.models.driver.balance.BalanceResponse
import com.dev.common.models.driver.requests.RequestActionResponse
import com.dev.common.models.driver.requests.RequestResponse
import com.dev.common.models.driver.requests.TripRequest
import com.dev.common.models.driver.trips.TripsResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.common.utils.AgriException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TripsRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val tripsObservable = MutableLiveData<Resource<TripsResponse>>()
    val requestsObservable = MutableLiveData<Resource<RequestResponse>>()
    val requestAcceptObservable = MutableLiveData<Resource<RequestActionResponse>>()
    val requestRejectObservable = MutableLiveData<Resource<RequestActionResponse>>()
    val requestBeginObservable = MutableLiveData<Resource<RequestActionResponse>>()
    val requestEndObservable = MutableLiveData<Resource<RequestActionResponse>>()
    val requestCurrentObservable = MutableLiveData<Resource<RequestActionResponse>>()
    val balanceObservable = MutableLiveData<Resource<BalanceResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getTrips() {
        setIsLoading(Observable.TRIPS)
        if (NetworkUtils.isConnected(context)) {
            executeTrips(Observable.TRIPS)
        } else {
            setNetworkError(Observable.TRIPS)
        }
    }

    fun getRequests() {
        setIsLoading(Observable.REQUESTS)
        if (NetworkUtils.isConnected(context)) {
            executeRequests(Observable.REQUESTS)
        } else {
            setNetworkError(Observable.REQUESTS)
        }
    }

    fun accept(id: TripRequest) {
        setIsLoading(Observable.TRIPACCEPT)
        if (NetworkUtils.isConnected(context)) {
            executeAcceptRequest(id, Observable.TRIPACCEPT)
        } else {
            setNetworkError(Observable.TRIPACCEPT)
        }
    }

    fun reject(id: TripRequest) {
        setIsLoading(Observable.TRIPREJECT)
        if (NetworkUtils.isConnected(context)) {
            executeRejectRequest(id, Observable.TRIPREJECT)
        } else {
            setNetworkError(Observable.TRIPREJECT)
        }
    }

    fun begin(id: TripRequest) {
        setIsLoading(Observable.TRIPBEGIN)
        if (NetworkUtils.isConnected(context)) {
            executeBeginRequest(id, Observable.TRIPBEGIN)
        } else {
            setNetworkError(Observable.TRIPBEGIN)
        }
    }

    fun end(id: TripRequest) {
        setIsLoading(Observable.TRIPEND)
        if (NetworkUtils.isConnected(context)) {
            executeEndRequest(id, Observable.TRIPEND)
        } else {
            setNetworkError(Observable.TRIPEND)
        }
    }

    fun current() {
        setIsLoading(Observable.TRIPCURRENT)
        if (NetworkUtils.isConnected(context)) {
            executeCurrentRequest(Observable.TRIPCURRENT)
        } else {
            setNetworkError(Observable.TRIPCURRENT)
        }
    }


    fun balance(balanceQuery: BalanceQuery) {
        setIsLoading(Observable.BALANCE)
        if (NetworkUtils.isConnected(context)) {
            executeBalance(Observable.BALANCE, balanceQuery)
        } else {
            setNetworkError(Observable.BALANCE)
        }
    }


    private fun executeTrips(obs: Observable) {
        Log.d("ONRSTART", obs.name + "-->  GET CURRENT TRIPS")

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).getTrips()
            call.enqueue(object : Callback<TripsResponse> {
                override fun onFailure(call: Call<TripsResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<TripsResponse>?, response: Response<TripsResponse>?) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeRequests(obs: Observable) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).getRequests()
            call.enqueue(object : Callback<RequestResponse> {
                override fun onFailure(call: Call<RequestResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<RequestResponse>?, response: Response<RequestResponse>?) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeAcceptRequest(id: TripRequest, obs: Observable) {
        Log.d("ONRSTART", obs.name + "--> " + Gson().toJson(id))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).acceptTrip(id)
            call.enqueue(object : Callback<RequestActionResponse> {
                override fun onFailure(call: Call<RequestActionResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<RequestActionResponse>?,
                    response: Response<RequestActionResponse>?
                ) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeRejectRequest(id: TripRequest, obs: Observable) {
        Log.d("ONRSTART", obs.name + "--> " + Gson().toJson(id))


        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).rejectTrip(id)
            call.enqueue(object : Callback<RequestActionResponse> {
                override fun onFailure(call: Call<RequestActionResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<RequestActionResponse>?,
                    response: Response<RequestActionResponse>?
                ) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeBeginRequest(id: TripRequest, obs: Observable) {
        Log.d("ONRSTART", obs.name + "--> " + Gson().toJson(id))


        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).beginTrip(id)
            call.enqueue(object : Callback<RequestActionResponse> {
                override fun onFailure(call: Call<RequestActionResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<RequestActionResponse>?,
                    response: Response<RequestActionResponse>?
                ) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeEndRequest(id: TripRequest, obs: Observable) {
        Log.d("ONRSTART", obs.name + "--> " + Gson().toJson(id))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).endTrip(id)
            call.enqueue(object : Callback<RequestActionResponse> {
                override fun onFailure(call: Call<RequestActionResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<RequestActionResponse>?,
                    response: Response<RequestActionResponse>?
                ) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeCurrentRequest(obs: Observable) {

        Log.d("ONRSTART", obs.name + "-->  GET CURRENT REQUEST")

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).currentTrip()
            call.enqueue(object : Callback<RequestActionResponse> {
                override fun onFailure(call: Call<RequestActionResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<RequestActionResponse>?,
                    response: Response<RequestActionResponse>?
                ) {
                    onResponse(response, obs)
                }
            })
        }
    }

    private fun executeBalance(obs: Observable, balanceQuery: BalanceQuery) {

        Log.d("ONRSTART", obs.name + "-->  GET BALANCE  " + Gson().toJson(balanceQuery))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).balances(balanceQuery)
            call.enqueue(object : Callback<BalanceResponse> {
                override fun onFailure(call: Call<BalanceResponse>?, t: Throwable?) {
                    onFailure(obs, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<BalanceResponse>?,
                    response: Response<BalanceResponse>?
                ) {
                    onResponse(response, obs)
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
        Log.d("ONFAILURE", observable.name + "--> " + Gson().toJson(t))

        setIsError(observable, t.toString(), agriException)
    }

    private fun <T> onResponse(response: Response<T>?, observable: Observable) {

        Log.d("ONRESPONSE", observable.name + "--> " + Gson().toJson(response))

        when (observable) {
            Observable.TRIPS -> onResponseTrips(response as Response<TripsResponse>, observable)
            Observable.REQUESTS -> onResponseRequests(response as Response<RequestResponse>, observable)
            Observable.TRIPACCEPT -> onResponseTrip(response as Response<RequestActionResponse>, observable)
            Observable.TRIPREJECT -> onResponseTrip(response as Response<RequestActionResponse>, observable)
            Observable.TRIPBEGIN -> onResponseTrip(response as Response<RequestActionResponse>, observable)
            Observable.TRIPEND -> onResponseTrip(response as Response<RequestActionResponse>, observable)
            Observable.TRIPCURRENT -> onResponseTrip(response as Response<RequestActionResponse>, observable)
            Observable.BALANCE -> onResponseBalance(response as Response<BalanceResponse>, observable)

        }


    }

    private fun onResponseTrips(response: Response<TripsResponse>?, observable: Observable) {

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

    private fun onResponseTrip(response: Response<RequestActionResponse>?, observable: Observable) {

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

    private fun onResponseRequests(response: Response<RequestResponse>?, observable: Observable) {

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

    private fun onResponseBalance(response: Response<BalanceResponse>?, observable: Observable) {

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
            Observable.TRIPS -> tripsObservable.postValue(Resource.loading(null))
            Observable.REQUESTS -> requestsObservable.postValue(Resource.loading(null))
            Observable.TRIPACCEPT -> requestAcceptObservable.postValue(Resource.loading(null))
            Observable.TRIPREJECT -> requestRejectObservable.postValue(Resource.loading(null))
            Observable.TRIPBEGIN -> requestBeginObservable.postValue(Resource.loading(null))
            Observable.TRIPEND -> requestEndObservable.postValue(Resource.loading(null))
            Observable.TRIPCURRENT -> requestCurrentObservable.postValue(Resource.loading(null))
            Observable.BALANCE -> balanceObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.TRIPS -> tripsObservable.postValue(Resource.success(data as TripsResponse))
            Observable.REQUESTS -> requestsObservable.postValue(Resource.success(data as RequestResponse))
            Observable.TRIPACCEPT -> requestAcceptObservable.postValue(Resource.success(data as RequestActionResponse))
            Observable.TRIPREJECT -> requestRejectObservable.postValue(Resource.success(data as RequestActionResponse))
            Observable.TRIPBEGIN -> requestBeginObservable.postValue(Resource.success(data as RequestActionResponse))
            Observable.TRIPEND -> requestEndObservable.postValue(Resource.success(data as RequestActionResponse))
            Observable.TRIPCURRENT -> requestCurrentObservable.postValue(Resource.success(data as RequestActionResponse))
            Observable.BALANCE -> balanceObservable.postValue(Resource.success(data as BalanceResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.TRIPS -> tripsObservable.postValue(Resource.error(message, null, exception))
            Observable.REQUESTS -> requestsObservable.postValue(Resource.error(message, null, exception))
            Observable.TRIPACCEPT -> requestAcceptObservable.postValue(Resource.error(message, null, exception))
            Observable.TRIPREJECT -> requestRejectObservable.postValue(Resource.error(message, null, exception))
            Observable.TRIPBEGIN -> requestBeginObservable.postValue(Resource.error(message, null, exception))
            Observable.TRIPEND -> requestEndObservable.postValue(Resource.error(message, null, exception))
            Observable.TRIPCURRENT -> requestCurrentObservable.postValue(Resource.error(message, null, exception))
            Observable.BALANCE -> balanceObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        TRIPS,
        TRIPACCEPT,
        TRIPREJECT,
        TRIPBEGIN,
        TRIPEND,
        TRIPCURRENT,
        REQUESTS,
        BALANCE
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
