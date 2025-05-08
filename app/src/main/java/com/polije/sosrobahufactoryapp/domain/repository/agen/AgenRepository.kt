package com.polije.sosrobahufactoryapp.domain.repository.agen

import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusPesananMasukResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

interface AgenRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>
    suspend fun getDashboardAgen(): DataResult<DashboardAgenResponse, HttpErrorCode>

    fun getPesananMasukAgen(): Flow<PagingData<PesananMasukAgenDataItem>>
    suspend fun getDetailPesananMasukAgen(idOrder: Int): DataResult<DetailPesananMasukAgenResponse, HttpErrorCode>
    suspend fun updateStatusPesanan(idOrder : Int,status : Int) : DataResult<UpdateStatusPesananMasukResponse, HttpErrorCode>


    fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean>
    suspend fun logout()
}