package com.polije.sosrobahufactoryapp.data.pabrik.repository

import DashboardPabrikResponse
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.pabrik.source.local.TokenManager
import com.polije.sosrobahufactoryapp.data.model.DetailOrderResponse
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.PesananMasukItem
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.data.pabrik.source.remote.PabrikDatasource
import com.polije.sosrobahufactoryapp.data.pabrik.source.remote.paging.PesananMasukPagingSource
import com.polije.sosrobahufactoryapp.data.pabrik.source.remote.paging.RiwayatRestockPagingSource
import com.polije.sosrobahufactoryapp.domain.pabrik.repositiory.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PabrikRepositoryImpl(val pabrikDatasource: PabrikDatasource, val tokenManager: TokenManager) :
    PabrikRepository {
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, String> {
        val request = LoginRequest(username, password)
        return try {
            val data = pabrikDatasource.login(request)
            tokenManager.saveToken(data.token?.plainTextToken ?: "")
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.cause.toString(), e.message.toString())
        }
    }

    override suspend fun getDashboardPabrik(): DataResult<DashboardPabrikResponse, String> {

        return try {
            val token = tokenManager.getToken().first()
            val data = pabrikDatasource.getDashboardPabrik("Bearer $token")
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error("Not Found", e.message.toString())
        }
    }

    override suspend fun getDetailPesananMasuk(idOrder: Int) : DataResult<DetailOrderResponse, String> {
        return try {
            val token = "Bearer ${tokenManager.getToken().first()}"
            val data = pabrikDatasource.getDetailPesananMasuk(token,idOrder)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error("Not Found", e.message.toString())
        }
    }

    override fun getPesananMasuk(): Flow<PagingData<PesananMasukItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = PesananMasukPagingSource.PESANAN_MASUK_PAGE_SIZE,
                pageSize = PesananMasukPagingSource.PESANAN_MASUK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PesananMasukPagingSource(pabrikDatasource, tokenManager) }).flow
    }

    override fun getRiwayatRestockPabrik(query: String): Flow<PagingData<RiwayatRestockItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = RiwayatRestockPagingSource.RIWAYAT_RESTOCK_PAGE_SIZE,
                pageSize = RiwayatRestockPagingSource.RIWAYAT_RESTOCK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RiwayatRestockPagingSource(query,pabrikDatasource, tokenManager)
            }
        ).flow
    }


    override suspend fun getToken(): String {
        return tokenManager.getToken().first()
    }

    override suspend fun logout() {
        tokenManager.removeToken()
    }

    override suspend fun getItemRestock(): DataResult<ProdukRestok, String> {
        return try {
            val token = tokenManager.getToken().first()
            val data = pabrikDatasource.getRestockItem("Bearer $token")
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error("Not Found", e.message.toString())
        }
    }
}