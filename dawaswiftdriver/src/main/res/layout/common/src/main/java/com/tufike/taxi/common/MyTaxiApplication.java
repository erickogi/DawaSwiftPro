package com.tufike.taxi.common;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

public class MyTaxiApplication extends Application {
    @Override
    public void onCreate() {
        FirebaseApp.initializeApp(getApplicationContext());
        int nightMode = AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        /*LocaleHelper.setLocale(base,"en");*/
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}