package com.polije.sosrobahufactoryapp.utils

sealed class DataResult<out D,out E>{
    data class Success<D>(val data: D): DataResult<D, Nothing>()
    data class Error< E>(val error : E): DataResult<Nothing, E>()
}