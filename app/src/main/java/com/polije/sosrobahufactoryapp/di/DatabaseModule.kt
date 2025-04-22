package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.datasource.local.TokenManager
import com.polije.sosrobahufactoryapp.data.datasource.local.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        TokenManager(androidContext().dataStore)
    }
}