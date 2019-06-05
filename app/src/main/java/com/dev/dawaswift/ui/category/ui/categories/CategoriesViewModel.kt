package com.dev.dawaswift.ui.category.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.dev.common.data.repository.OauthRepository
import com.dev.common.models.custom.Resource
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.models.Product.ProductResponse
import com.dev.dawaswift.models.Product.ProductSearchAndFilter
import com.dev.dawaswift.models.Product.ProductsResponse
import com.dev.dawaswift.models.category.CategoriesResponse
import com.dev.dawaswift.models.category.HealthAreasResponse
import com.dev.dawaswift.repository.CategoriesRepository
import com.dev.dawaswift.repository.HealthAreaRepository
import com.dev.dawaswift.repository.ProductsRepository

class CategoriesViewModel (application: Application) : AndroidViewModel(application) {
    private var oauthRepository: OauthRepository = OauthRepository(application)
    private var productsRepository: ProductsRepository = ProductsRepository(application)
    private var categoriesRepository: CategoriesRepository = CategoriesRepository(application)
    private var healthAreaRepository: HealthAreaRepository = HealthAreaRepository(application)

    private val switchProfileObservable = MediatorLiveData<Resource<Oauth>>()


    private val productsObservable = MediatorLiveData<Resource<ProductsResponse>>()
    private val popularProductsObservable = MediatorLiveData<Resource<ProductsResponse>>()
    private val favoriteObservable = MediatorLiveData<Resource<ProductsResponse>>()
    private val productObservable = MediatorLiveData<Resource<ProductResponse>>()

    private val categoriesObservable = MediatorLiveData<Resource<CategoriesResponse>>()

    private val healthAreascategoriesObservable = MediatorLiveData<Resource<HealthAreasResponse>>()



    init {
        switchProfileObservable.addSource(oauthRepository.switchProfileObservable) { data ->
            switchProfileObservable.setValue(
                data
            )
        }
        productsObservable.addSource(productsRepository.productsObservable) { data ->
            productsObservable.setValue(
                data
            )
        }
        popularProductsObservable.addSource(productsRepository.popularProductsObservable) { data ->
            popularProductsObservable.setValue(
                data
            )
        }
        favoriteObservable.addSource(productsRepository.favoriteObservable) { data ->
            favoriteObservable.setValue(
                data
            )
        }
        productObservable.addSource(productsRepository.productObservable) { data ->
            productObservable.setValue(
                data
            )
        }
        categoriesObservable.addSource(categoriesRepository.categoriesObservable) { data ->
            categoriesObservable.setValue(
                data
            )
        }
        healthAreascategoriesObservable.addSource(healthAreaRepository.healthAreascategoriesObservable) { data ->
            healthAreascategoriesObservable.setValue(
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

    fun observeSwitchProfile(): LiveData<Resource<Oauth>> {
        return switchProfileObservable
    }

    fun switchProfile(to: Int) {
        oauthRepository.switchProfile(to)
    }


    fun observePopularProducts(): LiveData<Resource<ProductsResponse>> {
        return popularProductsObservable
    }

    fun fetchPopularProducts(productSearchAndFilter: ProductSearchAndFilter) {
        productsRepository.getPopularProducts(productSearchAndFilter)
    }


    fun observeFavoriteProducts(): LiveData<Resource<ProductsResponse>> {
        return favoriteObservable
    }

    fun fetchFavoriteProducts( ) {
        productsRepository.getFavorite()
    }


    fun observeProducts(): LiveData<Resource<ProductsResponse>> {
        return popularProductsObservable
    }

    fun fetchProducts(productSearchAndFilter: ProductSearchAndFilter) {
        productsRepository.getProducts(productSearchAndFilter)
    }


    fun observeProduct(): LiveData<Resource<ProductResponse>> {
        return productObservable
    }

    fun fetchProduct(product: Product) {
        productsRepository.getProduct(product)
    }


    fun observeCategories(): LiveData<Resource<CategoriesResponse>> {
        return categoriesObservable
    }

    fun fetchCategories() {
        categoriesRepository.getCategories()
    }
    fun observeHealthAreas(): LiveData<Resource<HealthAreasResponse>> {
        return healthAreascategoriesObservable
    }

    fun fetchHealthAreas() {
        healthAreaRepository.getHealthAreas()
    }


}
