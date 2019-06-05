package com.dev.common.data.local

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson


class Converters {
    var gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Uri? {

        return if (value == null) {
            null
        } else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        return uri.toString()
    }

}
