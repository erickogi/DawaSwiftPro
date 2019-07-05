package com.dev.common.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.common.data.local.daos.ProfileDao
import com.dev.common.data.local.daos.SearchDao
import com.dev.common.models.ProductSearchAndFilter
import com.dev.common.models.oauth.Profile

@Database(entities = arrayOf(Profile::class, ProductSearchAndFilter::class), version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getDatabase(context: Context): AppDatabase? {
            synchronized(AppDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "dawaswiftxxdatabase"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

    abstract fun profileDao(): ProfileDao
    abstract fun searchDao(): SearchDao


}
