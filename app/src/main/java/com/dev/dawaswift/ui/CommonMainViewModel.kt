package com.dev.dawaswift.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dev.common.data.repository.OauthRepository
import com.dev.common.models.ProductSearchAndFilter
import com.dev.common.models.custom.Resource
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.models.oauth.ImageUploadResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.dawaswift.data.CartRepository
import com.dev.dawaswift.data.ChatRepository
import com.dev.dawaswift.data.DeliveryRepository
import com.dev.dawaswift.data.OrderRepository
import com.dev.dawaswift.models.Address.Address
import com.dev.dawaswift.models.Address.AddressResponse
import com.dev.dawaswift.models.Address.AddressesResponse
import com.dev.dawaswift.models.Address.SelectedAddress
import com.dev.dawaswift.models.Order.OrderResponse
import com.dev.dawaswift.models.Product.ProductResponse
import com.dev.dawaswift.models.Product.ProductsResponse
import com.dev.dawaswift.models.Product.TagsResponse
import com.dev.dawaswift.models.cart.*
import com.dev.dawaswift.models.category.CategoriesResponse
import com.dev.dawaswift.models.category.HealthAreasResponse
import com.dev.dawaswift.models.chat.Message
import com.dev.dawaswift.models.chat.MessageResponse
import com.dev.dawaswift.models.chat.MessageResponses
import com.dev.dawaswift.models.checkout.CheckOutModel
import com.dev.dawaswift.models.pharmacy.PharmacyResponse
import com.dev.dawaswift.repository.CategoriesRepository
import com.dev.dawaswift.repository.HealthAreaRepository
import com.dev.dawaswift.repository.PharmacyRepository
import com.dev.dawaswift.repository.ProductsRepository

class CommonMainViewModel(application: Application) : AndroidViewModel(application) {
    private var oauthRepository: OauthRepository = OauthRepository(application)
    private var productsRepository: ProductsRepository = ProductsRepository(application)
    private var categoriesRepository: CategoriesRepository = CategoriesRepository(application)
    private var ordersRepository: OrderRepository = OrderRepository(application)
    private var cartRepository: CartRepository = CartRepository(application)
    private var deliveryRepository: DeliveryRepository = DeliveryRepository(application)
    private var chatRepository: ChatRepository = ChatRepository(application)

    private val uploadImageObservable = MediatorLiveData<Resource<ImageUploadResponse>>()


    private var healthAreaRepository: HealthAreaRepository = HealthAreaRepository(application)
    private var pharmacyRepository: PharmacyRepository = PharmacyRepository(application)


    private val productsObservable = MediatorLiveData<Resource<ProductsResponse>>()
    private val pharmaciesObservable = MediatorLiveData<Resource<PharmacyResponse>>()
    private val deliveriesObservable = MediatorLiveData<Resource<AddressesResponse>>()
    private val deliveriesActionObservable = MediatorLiveData<Resource<AddressResponse>>()
    private val cartObservable = MediatorLiveData<Resource<CartResponse>>()
    private val popularProductsObservable = MediatorLiveData<Resource<ProductsResponse>>()
    private val favoriteObservable = MediatorLiveData<Resource<ProductsResponse>>()
    private val productObservable = MediatorLiveData<Resource<ProductResponse>>()
    private val createProductObservable = MediatorLiveData<Resource<ProductResponse>>()
    private val ordersObservable = MediatorLiveData<Resource<OrderResponse>>()
    private val checkoutObservable = MediatorLiveData<Resource<StkResponseBody>>()
    private val stepOneObservable = MediatorLiveData<Resource<StepOneResponse>>()
    private val tagsObservable = MediatorLiveData<Resource<TagsResponse>>()


    private val createChatObservable = MediatorLiveData<Resource<MessageResponse>>()
    private val chartObservable = MediatorLiveData<Resource<MessageResponses>>()


    private val healthAreascategoriesObservable = MediatorLiveData<Resource<HealthAreasResponse>>()


    private val deleteProductObservable = MediatorLiveData<Resource<ProductResponse>>()
    private val updateProductObservable = MediatorLiveData<Resource<ProductResponse>>()


    private val categoriesObservable = MediatorLiveData<Resource<CategoriesResponse>>()


    init {
        uploadImageObservable.addSource(oauthRepository.uploadImageObservable) { data ->
            uploadImageObservable.setValue(
                data
            )
        }


        ordersObservable.addSource(ordersRepository.ordersObservable) { data ->
            ordersObservable.setValue(
                data
            )
        }
        checkoutObservable.addSource(cartRepository.checkoutObservable) { data ->
            checkoutObservable.setValue(
                data
            )
        }
        cartObservable.addSource(cartRepository.cartObservable) { data ->
            cartObservable.setValue(
                data
            )
        }
        productsObservable.addSource(productsRepository.productsObservable) { data ->
            productsObservable.setValue(
                data
            )
        }
        tagsObservable.addSource(productsRepository.tagsObservable) { data ->
            tagsObservable.setValue(
                data
            )
        }

        popularProductsObservable.addSource(productsRepository.popularProductsObservable) { data ->
            popularProductsObservable.setValue(
                data
            )
        }
        pharmaciesObservable.addSource(pharmacyRepository.pharmaciesObservable) { data ->
            pharmaciesObservable.setValue(
                data
            )
        }



        stepOneObservable.addSource(cartRepository.stepOneObservable) { data ->
            stepOneObservable.setValue(
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
        deliveriesObservable.addSource(deliveryRepository.deliveriesObservable) { data ->
            deliveriesObservable.setValue(
                data
            )
        }
        deliveriesActionObservable.addSource(deliveryRepository.deliveriesActionObservable) { data ->
            deliveriesActionObservable.setValue(
                data
            )
        }




        healthAreascategoriesObservable.addSource(healthAreaRepository.healthAreascategoriesObservable) { data ->
            healthAreascategoriesObservable.setValue(
                data
            )
        }
        chartObservable.addSource(chatRepository.chartObservable) { data ->
            chartObservable.setValue(
                data
            )
        }
        createChatObservable.addSource(chatRepository.createChatObservable) { data ->
            createChatObservable.setValue(
                data
            )
        }


    }

    //GET SERVICES
    fun observeUploadImage(): LiveData<Resource<ImageUploadResponse>> {
        return uploadImageObservable
    }

    fun uploadImage(uri: Uri) {
        oauthRepository.uploadImage(uri)

    }

    fun saveSearch(searchAndFilter: ProductSearchAndFilter) {
        productsRepository.saveSearch(searchAndFilter)
    }

    fun getSearch(): LiveData<ProductSearchAndFilter> {
        return productsRepository.getSearch()
    }

    fun fetchSearch(): ProductSearchAndFilter {
        return productsRepository.fetchSearch()
    }


    fun observeCart(): LiveData<Resource<CartResponse>> {
        return cartObservable
    }

    fun addCart(addItem: AddItem) {
        cartRepository.cartAction(addItem)
    }

    fun viewCart() {
        cartRepository.getCart()
    }


    fun observeAddress(): LiveData<Resource<AddressesResponse>> {
        return deliveriesObservable
    }

    fun observeAddresAction(): LiveData<Resource<AddressResponse>> {
        return deliveriesActionObservable
    }

    fun fetchAddress() {
        deliveryRepository.getAddress()
    }

    fun createAddress(addItem: Address) {
        deliveryRepository.createAddress(addItem)
    }

    fun editAddress(addItem: Address) {
        deliveryRepository.editAddress(addItem)
    }

    fun deleteAddress(addItem: Address) {
        deliveryRepository.deleteAddress(addItem)
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


    fun observePopularProducts(): LiveData<Resource<ProductsResponse>> {
        return popularProductsObservable
    }


    fun observeFavoriteProducts(): LiveData<Resource<ProductsResponse>> {
        return favoriteObservable
    }


    fun observeCreateProducts(): LiveData<Resource<ProductResponse>> {
        return createProductObservable
    }


    fun observeProducts(): LiveData<Resource<ProductsResponse>> {
        return productsObservable
    }

    fun fetchProducts(productSearchAndFilter: ProductSearchAndFilter) {
        productsRepository.getProducts(productSearchAndFilter)
    }


    fun observeCreateChat(): LiveData<Resource<MessageResponse>> {
        return createChatObservable
    }

    fun createChats(message: Message) {
        chatRepository.create(message)
    }


    fun observeChats(): LiveData<Resource<MessageResponses>> {
        return chartObservable
    }

    fun fetchChats() {
        chatRepository.chats()
    }


    fun observeCategories(): LiveData<Resource<CategoriesResponse>> {
        return categoriesObservable
    }

    fun fetchCategories() {
        categoriesRepository.getCategories()
    }


    fun fetchOrders() {

        ordersRepository.getCompletedOrders()
    }

    fun observeOrders(): LiveData<Resource<OrderResponse>> {
        return ordersObservable
    }

    fun observeCheckOut(): LiveData<Resource<StkResponseBody>> {
        return checkoutObservable
    }


    fun checkOut(checkOutModel: CheckOutModel) {
        cartRepository.checkOut(checkOutModel)
    }

    fun deleteFormCart(item: CartItem?) {
        cartRepository.removeItem(item)

    }


    fun observeStepOne(): LiveData<Resource<StepOneResponse>> {
        return stepOneObservable
    }

    fun stepOne(address: SelectedAddress) {

        cartRepository.stepOne(address)
    }


    fun tags() {
        productsRepository.getTags()
    }

    fun observeTags(): LiveData<Resource<TagsResponse>> {
        return tagsObservable
    }


    fun observeHealthAreas(): LiveData<Resource<HealthAreasResponse>> {
        return healthAreascategoriesObservable
    }

    fun fetchHealthAreas() {
        healthAreaRepository.getHealthAreas()
    }

    fun pharmacies(locationSearchModel: LocationSearchModel) {

        pharmacyRepository.getPharmacies(locationSearchModel)

    }

    fun observePhamacies(): LiveData<Resource<PharmacyResponse>> {

        return pharmaciesObservable
    }

}
