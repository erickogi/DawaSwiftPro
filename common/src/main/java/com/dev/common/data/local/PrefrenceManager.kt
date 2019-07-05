package com.dev.common.data.local

import android.content.Context
import android.content.SharedPreferences
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.google.gson.Gson

class PrefrenceManager(internal var _context: Context) {

    internal var pref: SharedPreferences

    internal var editor: SharedPreferences.Editor

    internal var PRIVATE_MODE = 0


    companion object {

        private val LOGIN_STATUS = "LOGIN_STATUS"
        private val PROFILE = "cabinx_user_profile"
        private val FIREBASE_TOKEN = "firebasetoken"
        private val PREF_NAME = "cabinx_prefrences"

    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }


    fun clearUser() {
        editor.clear()
        editor.commit()
    }

    fun setFirebase(token: String) {
        editor.putString(FIREBASE_TOKEN, token)
        editor.commit()


    }

    fun getFirebase(): String {
        return pref.getString(FIREBASE_TOKEN, "")!!
    }

    fun setLoginStatus(status: Int) {
        editor.putInt(LOGIN_STATUS, status)
        editor.commit()
    }

    fun getLoginStatus(): Int {

        return pref.getInt(LOGIN_STATUS, 0)
    }

    fun saveProfile(data: Oauth) {

        editor.putString(PROFILE, Gson().toJson(data))
        editor.commit()
    }

    fun getProfile(): Oauth {
        var oauth = Oauth(Profile())

        try {
            if (pref.getString(PROFILE, "") != "") {
                oauth = Gson().fromJson(pref.getString(PROFILE, ""), Oauth::class.java)

            }
        } catch (e: Exception) {

        }
        return oauth

    }


}