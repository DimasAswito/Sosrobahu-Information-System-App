package com.polije.sosrobahufactoryapp.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginViewModel

class FactoryViewModel(private val context: Context) : ViewModel() {

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(FactoryLoginViewModel::class.java) -> {
                    @Suppress("UNCHECKED_CAST")
                    FactoryLoginViewModel(context) as T
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}

