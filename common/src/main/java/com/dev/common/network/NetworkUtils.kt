/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:27 AM
 *
 */

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.sql.DriverManager.println


class NetworkUtils {
    companion object {

        private fun getNetworkInfo(context: Context): NetworkInfo? {
            return try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                cm.activeNetworkInfo
            } catch (e: Exception) {
                println("CheckConnectivity Exception: " + e.message)
                null
            }
        }

        fun isConnectedToWifi(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
        }

        fun isConnectedToMobile(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
        }

        fun isConnected(context: Context, connectionType: Int): Boolean {
            when (connectionType) {
                ConnectivityManager.TYPE_WIFI -> return isConnectedToWifi(context)
                ConnectivityManager.TYPE_MOBILE -> return isConnectedToMobile(context)
                else -> return isConnected(context)
            }
        }

        @Deprecated("")
        fun isOnline(context: Context): Boolean {
            return isConnected(context)
        }

        fun isConnected(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnectedOrConnecting
        }

        private val TAG = NetworkUtils::class.java.name
        fun hasInternetConnection(context: Context): Boolean {
            if (isConnected(context)) {
                try {
                    val urlc = URL("http://clients3.google.com/generate_204").openConnection() as HttpURLConnection
                    urlc.setRequestProperty("User-Agent", "Android")
                    urlc.setRequestProperty("Connection", "close")
                    urlc.connectTimeout = 1500
                    urlc.connect()
                    return urlc.responseCode === 204
                } catch (e: IOException) {
                    Log.e(TAG, "Error checking internet connection", e)
                }
            } else {
                Log.d(TAG, "No network available!")
            }
            return false
        }
    }
}
