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
import com.dev.dawaswift.models.Address.SelectedAddress
import com.dev.dawaswift.models.cart.*
import com.dev.dawaswift.models.checkout.CheckOutModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val cartObservable = MutableLiveData<Resource<CartResponse>>()
    val checkoutObservable = MutableLiveData<Resource<StkResponseBody>>()
    val stepOneObservable = MutableLiveData<Resource<StepOneResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getCart() {
        setIsLoading(Observable.CART)
        if (NetworkUtils.isConnected(context)) {
            executeCart()
        } else {
            setNetworkError(Observable.CART)
        }
    }

    fun cartAction(addItem: AddItem) {
        setIsLoading(Observable.ACTIONCART)
        if (NetworkUtils.isConnected(context)) {
            executeCartAction(addItem)
        } else {
            setNetworkError(Observable.ACTIONCART)
        }
    }

    fun removeItem(item: CartItem?) {
        setIsLoading(Observable.ACTIONCART)
        if (NetworkUtils.isConnected(context)) {
            executeCartRemove(item!!)
        } else {
            setNetworkError(Observable.ACTIONCART)
        }
    }

    fun checkOut(checkOutModel: CheckOutModel) {
        checkoutObservable.postValue(Resource.loading(null))

        if (NetworkUtils.isConnected(context)) {
            executeCheckout(checkOutModel)
        } else {
            checkoutObservable.postValue(
                Resource.error(
                    context.getString(R.string.network_issue_message), null,
                    AgriException(context.getString(R.string.network_issue_message))
                )
            )


        }
    }

    fun stepOne(selectedAddress: SelectedAddress) {
        setIsLoading(Observable.STEPONE)

        if (NetworkUtils.isConnected(context)) {
            executeStepOne(selectedAddress)
        } else {
            setNetworkError(Observable.STEPONE)


        }
    }

    private fun executeCart() {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).cartView()
            call.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>?, t: Throwable?) {
                    onFailure(Observable.CART, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<CartResponse>?, response: Response<CartResponse>?) {

                    onResponse(response, Observable.CART)
                }
            })
        }
    }

    private fun executeCartAction(addItem: AddItem) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).addToCart(addItem)
            call.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>?, t: Throwable?) {
                    onFailure(Observable.ACTIONCART, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<CartResponse>?, response: Response<CartResponse>?) {
                    onResponse(response, Observable.ACTIONCART)
                }
            })
        }
    }

    private fun executeCartRemove(item: CartItem) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).removeItem(item)
            call.enqueue(object : Callback<CartResponse> {
                override fun onFailure(call: Call<CartResponse>?, t: Throwable?) {
                    onFailure(Observable.ACTIONCART, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<CartResponse>?, response: Response<CartResponse>?) {
                    onResponse(response, Observable.ACTIONCART)
                }
            })
        }
    }

    fun executeCheckout(checkOutModel: CheckOutModel) {
        Log.d("jhvghcjvdsv", Gson().toJson(checkOutModel))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).checkout(checkOutModel)
            call.enqueue(object : Callback<StkResponseBody> {
                override fun onFailure(call: Call<StkResponseBody>?, t: Throwable?) {
                    Log.d("jhvghcjvdsv", Gson().toJson(t))

                    onFailure(Observable.CHECKOUT, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<StkResponseBody>?, response: Response<StkResponseBody>?) {
                    Log.d("jhvghcjvdsv", Gson().toJson(response))

                    onCheckOutResponse(response)
                }
            })
        }
    }

    fun executeStepOne(selectedAddress: SelectedAddress) {
        Log.d("checkoutsdf", Gson().toJson(selectedAddress))

        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token).checkoutstepone(selectedAddress)
            call.enqueue(object : Callback<StepOneResponse> {
                override fun onFailure(call: Call<StepOneResponse>?, t: Throwable?) {
                    Log.d("checkoutsdf", Gson().toJson(t))
                    // Log.d("checkoutsdf", Gson().toJson(call))

                    onFailure(Observable.ACTIONCART, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<StepOneResponse>?, response: Response<StepOneResponse>?) {
                    //  Log.d("checkoutsdf", Gson().toJson(call))
                    Log.d("checkoutsdf", Gson().toJson(response))

                    onStepOneResponse(response, Observable.STEPONE)
                }
            })
        }
    }

    private fun onCheckOutResponse(response: Response<StkResponseBody>?) {
        if (response != null) {
            Log.d("checkoutsdf", Gson().toJson(response.body()))

            checkoutObservable.postValue(Resource.success(response.body() as StkResponseBody))
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


    private fun onResponse(response: Response<CartResponse>?, observable: Observable) {

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

    private fun onStepOneResponse(response: Response<StepOneResponse>?, observable: Observable) {

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
            Observable.CART -> cartObservable.postValue(Resource.loading(null))
            Observable.ACTIONCART -> cartObservable.postValue(Resource.loading(null))
            Observable.CHECKOUT -> cartObservable.postValue(Resource.loading(null))
            Observable.STEPONE -> stepOneObservable.postValue(Resource.loading(null))

        }

    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {


        when (observable) {
            Observable.CART -> cartObservable.postValue(Resource.success(data as CartResponse))
            Observable.ACTIONCART -> cartObservable.postValue(Resource.success(data as CartResponse))
            Observable.CHECKOUT -> cartObservable.postValue(Resource.success(data as CartResponse))
            Observable.STEPONE -> stepOneObservable.postValue(Resource.success(data as StepOneResponse))

        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {

        when (observable) {
            Observable.CART -> cartObservable.postValue(Resource.error(message, null, exception))

            Observable.ACTIONCART -> cartObservable.postValue(Resource.error(message, null, exception))

            Observable.CHECKOUT -> cartObservable.postValue(Resource.error(message, null, exception))

            Observable.STEPONE -> stepOneObservable.postValue(Resource.error(message, null, exception))


        }
    }

    enum class Observable {
        CART, ACTIONCART, CHECKOUT, STEPONE
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