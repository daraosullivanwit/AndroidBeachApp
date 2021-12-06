package org.wit.beachapp.main

import android.app.Application
import org.wit.beachapp.models.BeachMemStore
import org.wit.beachapp.models.BeachStore
import org.wit.beachapp.models.BeachJSONStore
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {

    lateinit var beaches: BeachStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        beaches = BeachJSONStore(applicationContext)
        i("Beach started")
    }
}