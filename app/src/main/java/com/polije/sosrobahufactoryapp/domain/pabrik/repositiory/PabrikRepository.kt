package com.polije.sosrobahufactoryapp.domain.pabrik.repositiory

import DashboardPabrikResponse
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.DataItem
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.Flow


interface PabrikRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, String>
    suspend fun getDashboardPabrik(): DataResult<DashboardPabrikResponse, String>
    fun getPesananMasuk() : Flow<PagingData<DataItem>>
    suspend fun getToken() : String
    suspend fun logout()

}