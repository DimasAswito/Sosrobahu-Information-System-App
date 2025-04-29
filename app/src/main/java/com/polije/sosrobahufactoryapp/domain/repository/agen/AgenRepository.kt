package com.polije.sosrobahufactoryapp.domain.repository.agen

import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode

interface AgenRepository {
    suspend fun login(username : String , password : String) : DataResult<LoginResponse, HttpErrorCode>
}