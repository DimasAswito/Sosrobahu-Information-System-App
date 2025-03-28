package com.polije.sosrobahufactoryapp.di

import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.data.pabrik.source.remote.PabrikDatasource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {


    single {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Json.asConverterFactory("application/json".toMediaType())
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get())

            .build()
    }

    single {
        get<Retrofit>().create(PabrikDatasource::class.java)
    }

}