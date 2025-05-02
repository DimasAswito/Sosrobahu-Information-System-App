package com.polije.sosrobahufactoryapp.domain.repository.distributor

import DashboardDistributorResponse
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

interface DistributorRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>

    suspend fun getDashboardDistributor(): DataResult<DashboardDistributorResponse, HttpErrorCode>

    suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailPesananMasukDistributorResponse, HttpErrorCode>

    fun getPesananMasukDistributor(): Flow<PagingData<PesananMasukDistributorDataItem>>

    fun getRiwayatOrderDistributor(): Flow<PagingData<RiwayatOrderDistributorDataItem>>

    fun getUserDistributorSession(): Flow<UserSession>
    fun isUserIsLogged(): Flow<Boolean>
    suspend fun logout()

}