package com.polije.sosrobahufactoryapp.domain.repository.pabrik

import DashboardResponse
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderPabrikResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.pabrik.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

interface PabrikRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>

    suspend fun getDashboardPabrik(): DataResult<DashboardResponse, HttpErrorCode>
    suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailOrderPabrikResponse, HttpErrorCode>
    suspend fun updateDetailPesananMasuk(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateDetailPesananResponse, HttpErrorCode>

    fun getPesananMasuk(): Flow<PagingData<PesananMasukItem>>
    fun getRiwayatRestockPabrik(query: String): Flow<PagingData<RiwayatRestockItem>>



    suspend fun logout()
    fun isUserIsLogged(requiredUser : UserRole) : Flow<Boolean>
    suspend fun getItemRestock(): DataResult<ProdukRestok, HttpErrorCode>

    suspend fun insertRestock(orders: Map<String, Map<String, Int>>): DataResult<Boolean, HttpErrorCode>


}