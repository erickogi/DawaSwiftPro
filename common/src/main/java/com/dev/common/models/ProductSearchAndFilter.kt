package com.dev.common.models

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(
    indices = [(Index("id"))],
    primaryKeys = ["id"]
)
@JvmSuppressWildcards
class ProductSearchAndFilter {
    @SerializedName("id")
    @Expose
    var id: Int? = null


    @SerializedName("productId")
    @Expose
    var productId: Int? = null


    @SerializedName("categoryId")
    @Expose
    var categoryId: Int? = null


    @SerializedName("subCategoryId")
    @Expose
    var subCategoryId: Int? = null


    @SerializedName("subCategoryItemId")
    @Expose
    var subCategoryItemId: Int? = null


    @SerializedName("pharmacyId")
    @Expose
    var pharmacyId: Int? = null


    @SerializedName("healthAreaId")
    @Expose
    var healthAreaId: Int? = null

    @SerializedName("queryString")
    @Expose
    var queryString: String? = null


    var pharmacyName: String? = null
    var categoryName: String? = null
    var subcategoryName: String? = null
    var subcategoryItemName: String? = null
    var healthAreaName: String? = null


}