package com.mihir.testing

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}