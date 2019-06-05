/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 4:11 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 4:09 PM
 *
 */

import com.dev.common.models.location.LocationSearchModel
import com.dev.common.models.location.LocationsResponse
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.models.Product.ProductResponse
import com.dev.dawaswift.models.Product.ProductSearchAndFilter
import com.dev.dawaswift.models.Product.ProductsResponse
import com.dev.dawaswift.models.category.CategoriesResponse
import com.dev.dawaswift.models.category.HealthAreasResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author kogi
 */
interface CustomerEndPoints  {


    @POST("mostpopular.json")
    fun getProducts(@Body search: ProductSearchAndFilter): Call<ProductsResponse>

    @POST("mostpopular.json")
    fun getPopularProducts(@Body search: ProductSearchAndFilter): Call<ProductsResponse>

    @POST("mostpopular.json")
    fun getFavoriteProducts(@Body search: Profile): Call<ProductsResponse>


    @POST("product.json")
    fun getProduct(@Body search: Product): Call<ProductResponse>

    @POST("categories.json")
    fun getCategories(@Body search: Profile): Call<CategoriesResponse>

    @POST("healthareas.json")
    fun getHealthAreas(@Body search: Profile): Call<HealthAreasResponse>




}
