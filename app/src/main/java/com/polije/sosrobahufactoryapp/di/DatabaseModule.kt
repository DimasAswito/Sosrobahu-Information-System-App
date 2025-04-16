package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.pabrik.source.local.TokenManager
import com.polije.sosrobahufactoryapp.data.pabrik.source.local.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        TokenManager(androidContext().dataStore)
    }
}