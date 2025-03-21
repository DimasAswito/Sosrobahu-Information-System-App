package com.polije.sosrobahufactoryapp

import android.app.Application
import com.polije.sosrobahufactoryapp.di.appModule
import com.polije.sosrobahufactoryapp.di.databaseModule
import com.polije.sosrobahufactoryapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@Application)
            modules(databaseModule, networkModule, appModule)
        }
    }
}