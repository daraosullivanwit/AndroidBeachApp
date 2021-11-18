package org.wit.beachapp.main

import android.app.Application
import org.wit.beachapp.models.BeachMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val beaches = BeachMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Beach started")
    }
}