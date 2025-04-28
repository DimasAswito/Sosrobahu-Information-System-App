package com.polije.sosrobahufactoryapp.domain.repository.pabrik

import DashboardResponse
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.pabrik.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananResponse
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow

interface PabrikRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, String>

    suspend fun getDashboardPabrik(): DataResult<DashboardResponse, String>
    suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailOrderResponse, String>
    suspend fun updateDetailPesananMasuk(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateDetailPesananResponse, String>

    fun getPesananMasuk(): Flow<PagingData<PesananMasukItem>>
    fun getRiwayatRestockPabrik(query: String): Flow<PagingData<RiwayatRestockItem>>


    fun getUserPabrikSession() : Flow<UserSession>
    suspend fun logout()

    suspend fun getItemRestock(): DataResult<ProdukRestok, String>

    suspend fun insertRestock(orders: Map<String, Map<String, Int>>): DataResult<Boolean, String>


}