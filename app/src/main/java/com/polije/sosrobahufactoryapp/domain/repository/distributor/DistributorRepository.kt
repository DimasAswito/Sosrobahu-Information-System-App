package com.polije.sosrobahufactoryapp.domain.repository.distributor

import DashboardResponse
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

interface DistributorRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, String>

    suspend fun getDashboardDistributor(): DataResult<DashboardResponse, String>

    suspend fun getDetailPesananMasuk(idOrder : Int) : DataResult<DetailPesananMasukDistributorResponse, String>

//    suspend fun getPesananMasukDistributor() : Flow<PagingData<RiwayatOrderDistributorDataItem>>

     fun getRiwayatOrder() : Flow<PagingData<RiwayatOrderDistributorDataItem>>

    fun getUserDistributorSession() : Flow<UserSession>

    suspend fun logout()

}