package com.crearo.home

import android.app.Application
import timber.log.Timber

class HomeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}