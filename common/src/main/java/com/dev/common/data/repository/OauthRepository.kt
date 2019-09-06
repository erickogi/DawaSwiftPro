package com.dev.common.data.repository

import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.common.R
import com.dev.common.data.local.AppDatabase
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.models.NotificationsResponse
import com.dev.common.models.custom.Resource
import com.dev.common.models.oauth.ImageUploadResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.common.utils.AgriException
import com.dev.common.utils.ErrorUtils
import com.dev.common.utils.FailureUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OauthRepository(application: Application) {
    private val profileDao: ProfileDao

    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val verifyPhoneObserve = MutableLiveData<Resource<Oauth>>()
    val verifyEmailObserve = MutableLiveData<Resource<Oauth>>()
    val updatePasswordObservable = MutableLiveData<Resource<Oauth>>()
    val signInObserver = MutableLiveData<Resource<Oauth>>()
    val requestOtpObservable = MutableLiveData<Resource<Oauth>>()
    val confirmOtpObservable = MutableLiveData<Resource<Oauth>>()
    val updateProfileObservable = MutableLiveData<Resource<Oauth>>()
    val createProfileObservable = MutableLiveData<Resource<Oauth>>()
    val switchProfileObservable = MutableLiveData<Resource<Oauth>>()
    val uploadImageObservable = MutableLiveData<Resource<ImageUploadResponse>>()
    val notificationsObservable = MutableLiveData<Resource<NotificationsResponse>>()




    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun signIn(parameters: Oauth) {
        setIsLoading(Observable.SIGNIN)
        if (NetworkUtils.isConnected(context)) {
            excuteSignIn(parameters)
        } else {
            setNetworkError(Observable.SIGNIN)
        }
    }

    fun switchProfile(to: Int) {
        setIsLoading(Observable.SWITCH_PROFILE)
        if (NetworkUtils.isConnected(context)) {
            excuteSwitchRole(to)
        } else {
            setNetworkError(Observable.SWITCH_PROFILE)
        }

    }

    fun verifyEmail(oauth: Oauth) {
        setIsLoading(Observable.VERIFY_EMAIL)
        if (NetworkUtils.isConnected(context)) {
            excuteVerifyEmail(oauth)
        } else {
            setNetworkError(Observable.VERIFY_EMAIL)
        }
    }

    fun verifyPhone(parameters: Oauth) {
        setIsLoading(Observable.VERIFYID)
        if (NetworkUtils.isConnected(context)) {
            excuteVerifyPhone(parameters)
        } else {
            setNetworkError(Observable.VERIFYID)

        }
    }


    fun requestOtp(parameters: Oauth) {
        //firbase.postValue(Resource.loading(null))
        setIsLoading(Observable.REQUEST_OTP)
        if (NetworkUtils.isConnected(context)) {
            excuteRequestOtp(parameters)
        } else {
            setNetworkError(Observable.REQUEST_OTP)

        }
    }

    fun confirmOtp(parameters: Oauth) {
        setIsLoading(Observable.CONFIRM_OTP)
        if (NetworkUtils.isConnected(context)) {
            excuteConfirmOtp(parameters)
        } else {
            setNetworkError(Observable.CONFIRM_OTP)

        }
    }

    fun createAccount(parameters: Oauth) {
        setIsLoading(Observable.CREATE_PROFILE)
        if (NetworkUtils.isConnected(context)) {
            excuteCreateAccount(parameters)
        } else {
            setNetworkError(Observable.CREATE_PROFILE)

        }
    }

    fun notifications() {
        setIsLoading(Observable.NOTIFICATIONS)
        if (NetworkUtils.isConnected(context)) {
            excuteNotifications()
        } else {
            setNetworkError(Observable.NOTIFICATIONS)

        }
    }

    fun updateAccount(parameters: Oauth) {
        setIsLoading(Observable.UPDATE_PROFILE)
        if (NetworkUtils.isConnected(context)) {
            excuteUpdateAccount(parameters)
        } else {
            setNetworkError(Observable.UPDATE_PROFILE)

        }
    }

    fun updatePassword(oauth: Oauth) {
        setIsLoading(Observable.UPDATE_PASSWORD)
        if (NetworkUtils.isConnected(context)) {
            excuteUpdatePassword(oauth)
        } else {
            setNetworkError(Observable.UPDATE_PASSWORD)

        }
    }


    private fun excuteSignIn(parameters: Oauth) {
        Log.d("CallOnStart", Gson().toJson(parameters))
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").signIn(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.SIGNIN, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.SIGNIN)
                }
            })
        }
    }

    private fun excuteVerifyPhone(parameters: Oauth) {
        Log.d("CallOnStart", Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").verifyPhone(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.VERIFYID, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.VERIFYID)
                }
            })
        }
    }

    private fun excuteVerifyEmail(parameters: Oauth) {
        Log.d("CallOnStart", Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").verifyEmail(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.VERIFY_EMAIL, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.VERIFY_EMAIL)
                }
            })
        }
    }

    private fun excuteRequestOtp(parameters: Oauth) {
        Log.d("CallOnStart", Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").requestOtp(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.REQUEST_OTP,t, FailureUtils().parseError(call,t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                   onResponse(response, Observable.REQUEST_OTP)
                }
            })
        }

      //  requestOtpObservable.postValue(Resource.success(parameters))


    }

    private fun excuteConfirmOtp(parameters: Oauth) {
        Log.d("CallOnStart", Gson().toJson(parameters))
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").confirmOtp(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.CONFIRM_OTP, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.CONFIRM_OTP)
                }
            })
        }
    }

    private fun excuteCreateAccount(parameters: Oauth) {
        Log.d("CallOnStart", Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").createAccount(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.CREATE_PROFILE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.CREATE_PROFILE)
                }
            })
        }
    }

    private fun excuteSwitchRole(to: Int) {
        val profile: Profile = fetchProfile()
        profile.roleId = to

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(profile.token).updateAccount(profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.SWITCH_PROFILE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.SWITCH_PROFILE)
                }
            })
        }
    }


    private fun excuteUpdateAccount(parameters: Oauth) {
        Log.d("CallOnStart", "UPDATE ACCOUNT  " + Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(parameters.profile?.token).updateAccount(parameters.profile as Profile)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.UPDATE_PROFILE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.UPDATE_PROFILE)
                }
            })
        }
    }

    private fun excuteNotifications() {
        Log.d("CallOnStart", "NOTIFIATIONS  " + Gson().toJson(""))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(fetchProfile().token).getNotification()
            call.enqueue(object : Callback<NotificationsResponse> {
                override fun onFailure(call: Call<NotificationsResponse>?, t: Throwable?) {
                    onFailure(Observable.NOTIFICATIONS, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(
                    call: Call<NotificationsResponse>?,
                    response: Response<NotificationsResponse>?
                ) {
                    onResponseNotifications(response, Observable.NOTIFICATIONS)
                }
            })
        }
    }

    private fun excuteUpdatePassword(parameters: Oauth) {
        var outh: Profile = Profile()
        outh.password = parameters.profile?.password
        outh.mobile = parameters.profile?.mobile
        Log.d("CallOnStart", Gson().toJson(parameters))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService("").updatePassword(outh)
            call.enqueue(object : Callback<Oauth> {
                override fun onFailure(call: Call<Oauth>?, t: Throwable?) {
                    onFailure(Observable.UPDATE_PROFILE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<Oauth>?, response: Response<Oauth>?) {
                    onResponse(response, Observable.UPDATE_PROFILE)
                }
            })
        }
    }

    private fun excuteUpLoadImage(parameters: Uri) {
        var file = File(getPath(parameters))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

        val body = MultipartBody.Part.createFormData(
            "methodName[headerData][relation][relative_image]",
            file.name,
            requestFile
        )


        // Parsing any Media type file
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)


        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getImageService("").upload(1, filename, fileToUpload)
            call.enqueue(object : Callback<ImageUploadResponse> {
                override fun onFailure(call: Call<ImageUploadResponse>?, t: Throwable?) {
                    onFailure(Observable.UPLOAD_IMAGE, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<ImageUploadResponse>?, response: Response<ImageUploadResponse>?) {
                    onResponseImage(response, Observable.UPLOAD_IMAGE)
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
        Log.d("CallOnFailure", "" + observable.name + " ----- " + Gson().toJson(t))
        setIsError(observable, t.toString(), agriException)
    }

    private fun onResponse(response: Response<Oauth>?, observable: Observable) {
        Log.d("CallOnResponse", "" + observable.name + " ----- " + Gson().toJson(response))
        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.statusCode!! > 0 && response.body()?.success!!) {
                    setIsSuccesful(observable, response.body()!!)

                    if (observable == Observable.CREATE_PROFILE || observable == Observable.UPDATE_PROFILE) {
                        saveProfile(response.body() as Oauth)
                    }
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

    private fun onResponseNotifications(response: Response<NotificationsResponse>?, observable: Observable) {
        Log.d("CallOnResponse", "" + observable.name + " ----- " + Gson().toJson(response))
        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.statusCode!! > 0 && response.body()?.success!!) {
                    setIsSuccesful(observable, response.body()!!)

                    if (observable == Observable.CREATE_PROFILE || observable == Observable.UPDATE_PROFILE) {
                        saveProfile(response.body() as Oauth)
                    }
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

    private fun onResponseImage(response: Response<ImageUploadResponse>?, observable: Observable) {
        Log.d("CallOnResponse", "" + observable.name + " ----- " + Gson().toJson(response))
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
            Observable.VERIFYID -> verifyPhoneObserve.postValue(Resource.loading(null))
            Observable.SIGNIN -> signInObserver.postValue(Resource.loading(null))
            Observable.REQUEST_OTP -> requestOtpObservable.postValue(Resource.loading(null))
            Observable.CONFIRM_OTP -> confirmOtpObservable.postValue(Resource.loading(null))
            Observable.UPDATE_PROFILE -> updateProfileObservable.postValue(Resource.loading(null))
            Observable.CREATE_PROFILE -> createProfileObservable.postValue(Resource.loading(null))
            Observable.VERIFY_EMAIL -> verifyEmailObserve.postValue(Resource.loading(null))
            Observable.UPDATE_PASSWORD -> updatePasswordObservable.postValue(Resource.loading(null))
            Observable.SWITCH_PROFILE -> switchProfileObservable.postValue(Resource.loading(null))
            Observable.UPLOAD_IMAGE -> uploadImageObservable.postValue(Resource.loading(null))
            Observable.NOTIFICATIONS -> notificationsObservable.postValue(Resource.loading(null))

        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.VERIFYID -> verifyPhoneObserve.postValue(Resource.success(data as Oauth))
            Observable.SIGNIN -> signInObserver.postValue(Resource.success(data as Oauth))
            Observable.REQUEST_OTP -> requestOtpObservable.postValue(Resource.success(data as Oauth))
            Observable.CONFIRM_OTP -> confirmOtpObservable.postValue(Resource.success(data as Oauth))
            Observable.UPDATE_PROFILE -> updateProfileObservable.postValue(Resource.success(data as Oauth))
            Observable.CREATE_PROFILE -> createProfileObservable.postValue(Resource.success(data as Oauth))
            Observable.VERIFY_EMAIL -> verifyEmailObserve.postValue(Resource.success(data as Oauth))
            Observable.UPDATE_PASSWORD -> updatePasswordObservable.postValue(Resource.success(data as Oauth))
            Observable.SWITCH_PROFILE -> switchProfileObservable.postValue(Resource.success(data as Oauth))
            Observable.UPLOAD_IMAGE -> uploadImageObservable.postValue(Resource.success(data as ImageUploadResponse))
            Observable.NOTIFICATIONS -> notificationsObservable.postValue(Resource.success(data as NotificationsResponse))

        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.VERIFYID -> verifyPhoneObserve.postValue(Resource.error(message, null, exception))
            Observable.SIGNIN -> signInObserver.postValue(Resource.error(message, null, exception))
            Observable.REQUEST_OTP -> requestOtpObservable.postValue(Resource.error(message, null, exception))
            Observable.CONFIRM_OTP -> confirmOtpObservable.postValue(Resource.error(message, null, exception))
            Observable.UPDATE_PROFILE -> updateProfileObservable.postValue(Resource.error(message, null, exception))
            Observable.CREATE_PROFILE -> createProfileObservable.postValue(Resource.error(message, null, exception))
            Observable.VERIFY_EMAIL -> verifyEmailObserve.postValue(Resource.error(message, null, exception))
            Observable.UPDATE_PASSWORD -> updatePasswordObservable.postValue(Resource.error(message, null, exception))
            Observable.SWITCH_PROFILE -> switchProfileObservable.postValue(Resource.error(message, null, exception))
            Observable.UPLOAD_IMAGE -> uploadImageObservable.postValue(Resource.error(message, null, exception))
            Observable.NOTIFICATIONS -> notificationsObservable.postValue(Resource.error(message, null, exception))

        }
    }

    enum class Observable {
        VERIFYID,
        SIGNIN,
        VERIFY_EMAIL,
        REQUEST_OTP,
        CONFIRM_OTP,
        UPDATE_PROFILE,
        UPDATE_PASSWORD,
        UPLOAD_IMAGE,

        SWITCH_PROFILE,
        CREATE_PROFILE,
        NOTIFICATIONS
    }

    fun saveProfile(data: Oauth) {
        Log.d("AgriDb", Gson().toJson(data))
        profileDao.delete()
        if (data.profile?.firebaseToken == "" || data.profile?.firebaseToken == null) {
            data.profile?.firebaseToken = prefrenceManager.getFirebase()
            // updateAccount(data)
        }
        data.profile?.let { profileDao.insertProfile(it) }
        prefrenceManager.saveProfile(data)
    }

    fun getProfile(): LiveData<Profile> {
        return profileDao.getProfile()
    }

    fun fetchProfile(): Profile {
        var profile = profileDao.fetch()
        if (profile == null) {
            profile = Profile()
        }
        return profile
    }

    fun logOut() {
        prefrenceManager.setLoginStatus(0)
        prefrenceManager.clearUser()
        profileDao.delete()


    }

    fun getPath(uri: Uri): String? {
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor = context.contentResolver.query(uri, projection, null, null, null) ?: return null
//        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        cursor.moveToFirst()
//        val s = cursor.getString(columnIndex)
//        cursor.close()
        return uri.path
    }

    fun uploadImage(uri: Uri) {
        setIsLoading(Observable.UPLOAD_IMAGE)
        if (NetworkUtils.isConnected(context)) {
            excuteUpLoadImage(uri)
        } else {
            setNetworkError(Observable.UPLOAD_IMAGE)

        }


    }

}
