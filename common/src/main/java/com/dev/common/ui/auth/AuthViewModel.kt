package com.dev.common.ui.auth


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dev.common.data.repository.LocationRepository
import com.dev.common.data.repository.OauthRepository
import com.dev.common.models.NotificationsResponse
import com.dev.common.models.custom.Resource
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.models.location.LocationsResponse
import com.dev.common.models.oauth.ImageUploadResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private var oauthRepository: OauthRepository = OauthRepository(application)
    private var locationRepository: LocationRepository = LocationRepository(application)


    private val verifyPhoneObservable = MediatorLiveData<Resource<Oauth>>()
    private val verifyEmailObservable = MediatorLiveData<Resource<Oauth>>()
    private val signInObservable = MediatorLiveData<Resource<Oauth>>()
    private val requestOtpObservable = MediatorLiveData<Resource<Oauth>>()
    private val confirmOtpObservable = MediatorLiveData<Resource<Oauth>>()
    private val createProfileObservable = MediatorLiveData<Resource<Oauth>>()
    private val updateProfileObservable = MediatorLiveData<Resource<Oauth>>()
    private val updatePasswordObservable = MediatorLiveData<Resource<Oauth>>()
    private val uploadImageObservable = MediatorLiveData<Resource<ImageUploadResponse>>()
    private val notificationObservable = MediatorLiveData<Resource<NotificationsResponse>>()


    private val countiesObservable = MediatorLiveData<Resource<LocationsResponse>>()
    private val subContiesObservable = MediatorLiveData<Resource<LocationsResponse>>()
    private val wardsObservable = MediatorLiveData<Resource<LocationsResponse>>()


    init {
        uploadImageObservable.addSource(oauthRepository.uploadImageObservable) { data ->
            uploadImageObservable.setValue(
                data
            )
        }
        notificationObservable.addSource(oauthRepository.notificationsObservable) { data ->
            notificationObservable.setValue(
                data
            )
        }


        signInObservable.addSource(oauthRepository.signInObserver) { data -> signInObservable.setValue(data) }
        verifyPhoneObservable.addSource(oauthRepository.verifyPhoneObserve) { data ->
            verifyPhoneObservable.setValue(
                data
            )
        }
        verifyEmailObservable.addSource(oauthRepository.verifyEmailObserve) { data ->
            verifyEmailObservable.setValue(
                data
            )
        }
        requestOtpObservable.addSource(oauthRepository.requestOtpObservable) { data ->
            requestOtpObservable.setValue(
                data
            )
        }
        confirmOtpObservable.addSource(oauthRepository.confirmOtpObservable) { data ->
            confirmOtpObservable.setValue(
                data
            )
        }
        createProfileObservable.addSource(oauthRepository.createProfileObservable) { data ->
            createProfileObservable.setValue(
                data
            )
        }
        updateProfileObservable.addSource(oauthRepository.updateProfileObservable) { data ->
            updateProfileObservable.setValue(
                data
            )
        }
        updatePasswordObservable.addSource(oauthRepository.updatePasswordObservable) { data ->
            updatePasswordObservable.setValue(
                data
            )
        }

        countiesObservable.addSource(locationRepository.countiesObservable) { data -> countiesObservable.setValue(data) }
        subContiesObservable.addSource(locationRepository.subContiesObservable) { data ->
            subContiesObservable.setValue(
                data
            )
        }
        wardsObservable.addSource(locationRepository.wardsObservable) { data -> wardsObservable.setValue(data) }


    }

    //SIGN IN
    fun signIn(parameters: Oauth) {
        oauthRepository.signIn(parameters)
    }

    fun observeSignIn(): LiveData<Resource<Oauth>> {
        return signInObservable
    }

    //VERIFY PHONE
    fun verifyPhone(parameters: Oauth) {
        oauthRepository.verifyPhone(parameters)
    }

    fun observeVerifyPhone(): LiveData<Resource<Oauth>> {
        return verifyPhoneObservable
    }


    //REQUEST OTP
    fun requestOtp(parameters: Oauth) {
        oauthRepository.requestOtp(parameters)
    }

    fun observeRequestOtp(): LiveData<Resource<Oauth>> {
        return requestOtpObservable
    }

    //CONFIRM OTP
    fun confirmOtp(parameters: Oauth) {
        oauthRepository.confirmOtp(parameters)
    }

    fun observeConfirmOtp(): LiveData<Resource<Oauth>> {
        return confirmOtpObservable
    }

    fun observeUploadImage(): LiveData<Resource<ImageUploadResponse>> {
        return uploadImageObservable
    }

    fun uploadImage(uri: Uri) {
        oauthRepository.uploadImage(uri)

    }

    //UPDATE PROFILE
    fun updateProfile(parameters: Oauth) {
        oauthRepository.updateAccount(parameters)
    }

    fun observeUpdateProfile(): LiveData<Resource<Oauth>> {
        return updateProfileObservable
    }


    //CREATE PROFILE
    fun createProfile(parameters: Oauth) {
        oauthRepository.createAccount(parameters)
    }

    fun observeCreateProfile(): LiveData<Resource<Oauth>> {
        return createProfileObservable
    }


    //NOTITFICATIONS
    fun notifications() {
        oauthRepository.notifications()
    }

    fun observeNotifications(): LiveData<Resource<NotificationsResponse>> {
        return notificationObservable
    }


    //GET COUNTIES
    fun getCounties(parameters: LocationSearchModel) {
        locationRepository.getCounties(parameters)
    }

    fun observeCounties(): LiveData<Resource<LocationsResponse>> {
        return countiesObservable
    }


    //GET SUB_COUNTIES
    fun getSubCounties(parameters: LocationSearchModel) {
        locationRepository.getSubCounties(parameters)
    }

    fun observeSubCounties(): LiveData<Resource<LocationsResponse>> {
        return subContiesObservable
    }


    //GET WARDS
    fun getWards(parameters: LocationSearchModel) {
        locationRepository.getWards(parameters)
    }

    fun observeWards(): LiveData<Resource<LocationsResponse>> {
        return wardsObservable
    }


    fun getProfile(): Oauth {

        return Oauth(oauthRepository.fetchProfile())
    }

    fun liveProfile(): LiveData<Profile> {

        return oauthRepository.getProfile()
    }

    fun saveProfile(oauth: Oauth) {

        oauthRepository.saveProfile(oauth)
    }

    fun observeVerifyEmail(): LiveData<Resource<Oauth>> {
        return verifyEmailObservable
    }

    fun verifyEmail(oauth: Oauth) {

        oauthRepository.verifyEmail(oauth)
    }

    fun observeUpdatePassword(): LiveData<Resource<Oauth>> {
        return updatePasswordObservable
    }

    fun updatePassword(oauth: Oauth) {

        oauthRepository.updatePassword(oauth)
    }


}
