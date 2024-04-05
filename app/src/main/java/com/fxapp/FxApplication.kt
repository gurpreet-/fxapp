package com.fxapp

import android.app.Application
import android.os.Build
import com.fxapp.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


class FxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Enable logging framework
        if (Build.TYPE.contains("debug")) {
            plant(DebugTree())
        }

        // Start our DI framework
        startKoin {
            androidLogger()
            androidContext(this@FxApplication)
            modules(AppModule.allModules)
        }
    }
}