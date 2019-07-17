package com.dev.dawaswiftdriver.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dev.common.data.repository.OauthRepository
import com.dev.common.models.custom.Resource
import com.dev.common.models.driver.requests.RequestActionResponse
import com.dev.common.models.driver.requests.RequestResponse
import com.dev.common.models.driver.requests.TripRequest
import com.dev.common.models.driver.trips.TripsResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile

class TripsViewModel(application: Application) : AndroidViewModel(application) {
    private var oauthRepository: OauthRepository = OauthRepository(application)
    private var tripsRepository: TripsRepository = TripsRepository(application)


    val tripsObservable = MediatorLiveData<Resource<TripsResponse>>()
    val requestsObservable = MediatorLiveData<Resource<RequestResponse>>()
    val requestAcceptObservable = MediatorLiveData<Resource<RequestActionResponse>>()
    val requestRejectObservable = MediatorLiveData<Resource<RequestActionResponse>>()
    val requestBeginObservable = MediatorLiveData<Resource<RequestActionResponse>>()
    val requestEndObservable = MediatorLiveData<Resource<RequestActionResponse>>()
    val requestCurrentObservable = MediatorLiveData<Resource<RequestActionResponse>>()


    init {
        tripsObservable.addSource(tripsRepository.tripsObservable) { data -> tripsObservable.setValue(data) }

        requestsObservable.addSource(tripsRepository.requestsObservable) { data -> requestsObservable.setValue(data) }

        requestAcceptObservable.addSource(tripsRepository.requestAcceptObservable) { data ->
            requestAcceptObservable.setValue(
                data
            )
        }

        requestRejectObservable.addSource(tripsRepository.requestRejectObservable) { data ->
            requestRejectObservable.setValue(
                data
            )
        }

        requestBeginObservable.addSource(tripsRepository.requestBeginObservable) { data ->
            requestBeginObservable.setValue(
                data
            )
        }

        requestEndObservable.addSource(tripsRepository.requestEndObservable) { data ->
            requestEndObservable.setValue(
                data
            )
        }

        requestCurrentObservable.addSource(tripsRepository.requestCurrentObservable) { data ->
            requestCurrentObservable.setValue(
                data
            )
        }


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

    fun logout() {
        oauthRepository.logOut()
    }

    fun observeTrips(): LiveData<Resource<TripsResponse>> {
        return tripsObservable
    }

    fun trips() {
        tripsRepository.getTrips()
    }


    fun observeRequests(): LiveData<Resource<RequestResponse>> {
        return requestsObservable
    }

    fun requests() {
        tripsRepository.getRequests()
    }

    fun observeAccept(): LiveData<Resource<RequestActionResponse>> {
        return requestAcceptObservable
    }

    fun requestAccept(request: TripRequest) {
        tripsRepository.accept(request)
    }


    fun observeReject(): LiveData<Resource<RequestActionResponse>> {
        return requestRejectObservable
    }

    fun requestReject(request: TripRequest) {
        tripsRepository.reject(request)
    }


    fun observeBegin(): LiveData<Resource<RequestActionResponse>> {
        return requestBeginObservable
    }

    fun requestBegin(request: TripRequest) {
        tripsRepository.begin(request)
    }


    fun observeEnd(): LiveData<Resource<RequestActionResponse>> {
        return requestEndObservable
    }

    fun requestEnd(request: TripRequest) {
        tripsRepository.end(request)
    }

    fun observeCurrent(): LiveData<Resource<RequestActionResponse>> {
        return requestCurrentObservable
    }

    fun requestCurrent() {
        tripsRepository.current()
    }


}
