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
import com.dev.dawaswift.models.Product.ProductsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsRepository(application: Application) {
    private val profileDao: ProfileDao
    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)
    val productsObservable = MutableLiveData<Resource<ProductsResponse>>()
    val popularProductsObservable = MutableLiveData<Resource<ProductsResponse>>()
    val favoriteObservable = MutableLiveData<Resource<ProductsResponse>>()
    val productObservable = MutableLiveData<Resource<ProductResponse>>()

    private val context: Context

    init {
        profileDao = db.profileDao()
        context = application.applicationContext
    }

    fun getProducts(productSearchAndFilter: ProductSearchAndFilter) {
        setIsLoading(Observable.Products)
        if (NetworkUtils.isConnected(context)) {
            executeProducts(productSearchAndFilter)
        } else {
            setNetworkError(Observable.Products)
        }
    }

    fun getProduct(product: Product) {
        setIsLoading(Observable.Product)
        if (NetworkUtils.isConnected(context)) {
            executeProduct(product)
        } else {
            setNetworkError(Observable.Product)
        }
    }

    fun getPopularProducts(productSearchAndFilter: ProductSearchAndFilter) {
        setIsLoading(Observable.POPULAR_PRODUCTS)
        if (NetworkUtils.isConnected(context)) {
            executePopularProducts(productSearchAndFilter)
        } else {
            setNetworkError(Observable.POPULAR_PRODUCTS)
        }
    }

    fun getFavorite() {
        setIsLoading(Observable.FAVORITE_PRODUCTS)
        if (NetworkUtils.isConnected(context)) {
            executeFavoriteProducts()
        } else {
            setNetworkError(Observable.FAVORITE_PRODUCTS)
        }
    }


    private fun executeProducts(productSearchAndFilter: ProductSearchAndFilter) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token, "http://calista.co.ke/dawaswift_mock/").getProducts(productSearchAndFilter)
            call.enqueue(object : Callback<ProductsResponse> {
                override fun onFailure(call: Call<ProductsResponse>?, t: Throwable?) {
                    onFailure(Observable.Products, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<ProductsResponse>?, response: Response<ProductsResponse>?) {
                    onResponse(response, Observable.Products)
                }
            })
        }
    }

    private fun executePopularProducts(productSearchAndFilter: ProductSearchAndFilter) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token, "http://calista.co.ke/dawaswift_mock/").getPopularProducts(productSearchAndFilter)
            call.enqueue(object : Callback<ProductsResponse> {
                override fun onFailure(call: Call<ProductsResponse>?, t: Throwable?) {
                    onFailure(Observable.POPULAR_PRODUCTS, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<ProductsResponse>?, response: Response<ProductsResponse>?) {
                    onResponse(response, Observable.POPULAR_PRODUCTS)
                }
            })
        }
    }
    private fun executeProduct(product: Product) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token, "http://calista.co.ke/dawaswift_mock/").getProduct(product)
            call.enqueue(object : Callback<ProductResponse> {
                override fun onFailure(call: Call<ProductResponse>?, t: Throwable?) {
                    onFailure(Observable.Product, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<ProductResponse>?, response: Response<ProductResponse>?) {
                    onResponse(response, Observable.Product)
                }
            })
        }
    }

    private fun executeFavoriteProducts() {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = CustomerRequestService.getService(fetchProfile().token, "http://calista.co.ke/dawaswift_mock/").getFavoriteProducts(fetchProfile())
            call.enqueue(object : Callback<ProductsResponse> {
                override fun onFailure(call: Call<ProductsResponse>?, t: Throwable?) {
                    onFailure(Observable.FAVORITE_PRODUCTS, t, FailureUtils().parseError(call, t))
                }

                override fun onResponse(call: Call<ProductsResponse>?, response: Response<ProductsResponse>?) {
                    onResponse(response, Observable.FAVORITE_PRODUCTS)
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
        if(observable==Observable.Product){
            onResponseSingle(response as Response<ProductResponse>,observable)

        }else{
            onResponseMulti(response as Response<ProductsResponse>,observable)

        }
    }
    private fun onResponseMulti(response: Response<ProductsResponse>?, observable: Observable) {

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
    private fun onResponseSingle(response: Response<ProductResponse>?, observable: Observable) {

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
            Observable.Products -> productsObservable.postValue(Resource.loading(null))
            Observable.POPULAR_PRODUCTS -> popularProductsObservable.postValue(Resource.loading(null))
            Observable.FAVORITE_PRODUCTS -> favoriteObservable.postValue(Resource.loading(null))
            Observable.Product -> productObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.Products -> productsObservable.postValue(Resource.success(data as ProductsResponse))
            Observable.POPULAR_PRODUCTS -> popularProductsObservable.postValue(Resource.success(data as ProductsResponse))
            Observable.FAVORITE_PRODUCTS -> favoriteObservable.postValue(Resource.success(data as ProductsResponse))
            Observable.Product -> productObservable.postValue(Resource.success(data as ProductResponse))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AgriException) {
        when (observable) {
            Observable.Products -> productsObservable.postValue(Resource.error(message, null, exception))
            Observable.POPULAR_PRODUCTS -> popularProductsObservable.postValue(Resource.error(message, null, exception))
            Observable.FAVORITE_PRODUCTS -> favoriteObservable.postValue(Resource.error(message, null, exception))
            Observable.Product -> productObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        Products,
        Product,
        POPULAR_PRODUCTS,
        FAVORITE_PRODUCTS
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
