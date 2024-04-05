package com.fxapp

import android.app.Application
import com.fxapp.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start our DI framework
        startKoin {
            androidLogger()
            androidContext(this@FxApplication)
            modules(AppModule.allModules)
        }
    }
}