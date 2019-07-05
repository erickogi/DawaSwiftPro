package com.dev.dawaswift.data

import CustomerRequestService
import NetworkUtils
import android.app.Application
import android.content.Context
import android.util.Log
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
import com.dev.dawaswift.models.chat.Message
import com.dev.dawaswift.models.chat.MessageResponse
import com.dev.dawaswift.models.chat.MessageResponses
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val chartObservable = MutableLiveData<Resource<MessageResponses>>()
    val createChatObservable = MutableLiveData<Resource<MessageResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun chats() {
        setIsLoading(Observable.CHATS)
        if (NetworkUtils.isConnected(context)) {
            executeChats()
        } else {
            setNetworkError(Observable.CHATS)
        }
    }

    fun create(addItem: Message) {
        setIsLoading(Observable.CREATE)
        if (NetworkUtils.isConnected(context)) {
            executeCreateChat(addItem)
        } else {
            setNetworkError(Observable.CREATE)
        }
    }


    private fun executeChats() {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).chats()
            call.enqueue(object : Callback<MessageResponses> {
                override fun onFailure(call: Call<MessageResponses>?, t: Throwable?) {
                    onFailure(Observable.CHATS, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<MessageResponses>?, response: Response<MessageResponses>?) {

                    onResponseS(response, Observable.CHATS)
                }
            })
        }
    }

    private fun executeCreateChat(addItem: Message) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).createChat(addItem)
            call.enqueue(object : Callback<MessageResponse> {
                override fun onFailure(call: Call<MessageResponse>?, t: Throwable?) {
                    onFailure(Observable.CREATE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<MessageResponse>?, response: Response<MessageResponse>?) {
                    onResponse(response, Observable.CREATE)
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


    private fun onResponse(response: Response<MessageResponse>?, observable: Observable) {

        Log.d("cereerv", "" + Gson().toJson(response))
        if (response != null) {
            if (response.isSuccessful) {
                if (response.body() != null && response.body()?.statusCode != null && response.body()!!.statusCode!! > 0 && response.body()?.success!!) {

                    setIsSuccesful(observable, response.body()!!)
                    executeChats()


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

    private fun onResponseS(response: Response<MessageResponses>?, observable: Observable) {

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
            Observable.CHATS -> chartObservable.postValue(Resource.loading(null))
            Observable.CREATE -> createChatObservable.postValue(Resource.loading(null))

        }

    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {


        when (observable) {
            Observable.CHATS -> chartObservable.postValue(Resource.success(data as MessageResponses))
            Observable.CREATE -> createChatObservable.postValue(Resource.success(data as MessageResponse))

        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {

        when (observable) {
            Observable.CHATS -> chartObservable.postValue(Resource.error(message, null, exception))

            Observable.CREATE -> createChatObservable.postValue(Resource.error(message, null, exception))


        }
    }

    enum class Observable {
        CHATS, CREATE
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