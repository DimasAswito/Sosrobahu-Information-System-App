package com.polije.sosrobahufactoryapp.utils

sealed class DataResult<out D,out E:String>{
    data class Success<out D>(val data: D): DataResult<D, Nothing>()
    data class Error<out E: String>(val error: E,val message: String): DataResult<Nothing, E>()
}