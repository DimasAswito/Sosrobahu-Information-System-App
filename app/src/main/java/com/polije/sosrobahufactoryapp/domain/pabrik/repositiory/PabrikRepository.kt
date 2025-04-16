package com.polije.sosrobahufactoryapp.domain.pabrik.repositiory

import DashboardPabrikResponse
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.DetailOrderResponse
import com.polije.sosrobahufactoryapp.data.model.PesananMasukItem
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.Flow


interface PabrikRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, String>

    suspend fun getDashboardPabrik(): DataResult<DashboardPabrikResponse, String>
    suspend fun getDetailPesananMasuk(idOrder : Int): DataResult<DetailOrderResponse, String>

    fun getPesananMasuk() : Flow<PagingData<PesananMasukItem>>
    fun getRiwayatRestockPabrik(query : String) : Flow<PagingData<RiwayatRestockItem>>

    suspend fun getToken() : String
    suspend fun logout()

    suspend fun getItemRestock() : DataResult<ProdukRestok, String>

}