package com.dev.dawaswiftdriver

import android.app.Application
import com.rohitss.uceh.UCEHandler

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        UCEHandler.Builder(applicationContext).addCommaSeparatedEmailAddresses("erickogi14@gmail.com")
            .setUCEHEnabled(true).setBackgroundModeEnabled(true)
            .build()
    }
}