/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 4:11 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 4:09 PM
 *
 */

import com.dev.common.models.ProductSearchAndFilter
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.models.oauth.ImageUploadResponse
import com.dev.common.models.oauth.Profile
import com.dev.dawaswift.models.Address.Address
import com.dev.dawaswift.models.Address.AddressResponse
import com.dev.dawaswift.models.Address.AddressesResponse
import com.dev.dawaswift.models.Address.SelectedAddress
import com.dev.dawaswift.models.Order.OrderResponse
import com.dev.dawaswift.models.Product.Product
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author kogi
 */
interface CustomerEndPoints  {


    @POST("search/v2.php")
    fun getProducts(@Body search: ProductSearchAndFilter): Call<ProductsResponse>


    @POST("pharmacy/fetch.php")
    fun getPhamacies(@Body search: LocationSearchModel): Call<PharmacyResponse>


    @GET("search/tags.php")
    fun getTags(): Call<TagsResponse>


    @GET("product/mostpopular.php")
    fun getPopularProducts(): Call<ProductsResponse>

    @POST("search/v1.php")
    fun getFavoriteProducts(@Body search: Profile): Call<ProductsResponse>


    @POST("product.json")
    fun getProduct(@Body search: Product): Call<ProductResponse>

    @GET("categories/fetch.php")
    fun getCategories(): Call<CategoriesResponse>

    @GET("healthareas/fetch.php")
    fun getHealthAreas(): Call<HealthAreasResponse>


    @GET("healthareas/fetch.php")
    fun tags(): Call<HealthAreasResponse>


    @GET("deliveryaddress/fetch.php")
    fun deliveriesAddress(): Call<AddressesResponse>


    @POST("deliveryaddress/create.php")
    fun createDelivery(@Body search: Address): Call<AddressesResponse>


    @POST("deliveryaddress/edit.php")
    fun editDelivery(@Body search: Address): Call<AddressResponse>

    @POST("deliveryaddress/delete.php")
    fun deleteDelivery(@Body search: Address): Call<AddressesResponse>

    @POST("checkout/step1.php")
    fun checkoutstepone(@Body search: SelectedAddress): Call<StepOneResponse>


    @Multipart
    @POST("v3.php")
    fun upload(
        @Part("appId") appId: Int,
        @Part("file") re: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ImageUploadResponse>


    @POST("cart/additem.php")
    fun addToCart(@Body item: AddItem): Call<CartResponse>


    @POST("cart/removeitem.php")
    fun removeItem(@Body item: CartItem): Call<CartResponse>


    @POST("checkout/step2.php")
    fun checkout(@Body item: CheckOutModel): Call<StkResponseBody>


    @POST("cart/view.php")
    fun cartView(): Call<CartResponse>


    @GET("order/fetch.php")
    fun completedOrders(): Call<OrderResponse>


    @GET("chat/fetch.php")
    fun chats(): Call<MessageResponses>


    @POST("chat/send-message.php")
    fun createChat(@Body item: Message): Call<MessageResponse>







}
