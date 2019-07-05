package com.dev.common.data.local.daos


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.dev.common.models.ProductSearchAndFilter

@Dao
interface SearchDao {
    @Query("SELECT * FROM PRODUCTSEARCHANDFILTER LIMIT 1 ")
    fun getSearch(): LiveData<ProductSearchAndFilter>

    @Query("SELECT * FROM PRODUCTSEARCHANDFILTER LIMIT 1")
    fun fetch(): ProductSearchAndFilter

    @Insert(onConflict = REPLACE)
    fun insert(model: ProductSearchAndFilter)

    @Update
    fun update(model: ProductSearchAndFilter)

    @Delete
    fun delete(model: ProductSearchAndFilter)

    @Query("DELETE FROM PRODUCTSEARCHANDFILTER")
    fun delete()
}
