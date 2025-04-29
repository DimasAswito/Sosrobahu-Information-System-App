package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        SessionManager(androidContext())
    }
}