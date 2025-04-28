package com.polije.sosrobahufactoryapp.data.repository

import DashboardResponse
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.datasource.local.TokenManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.DistributorDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.RiwayatOrderPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.pabrik.paging.RiwayatRestockPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class DistributorRepositoryImpl(
    val distributorDatasource: DistributorDatasource,
    val tokenManager: TokenManager
) :
    DistributorRepository {
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, String> {
        val request = LoginRequest(username, password)
        return try {
            val data = distributorDatasource.login(request)
            tokenManager.saveToken(data.token?.plainTextToken ?: "")
            tokenManager.saveUserRole(UserRole.DISTRIBUTOR)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.cause.toString(), e.message.toString())
        }
    }

    override suspend fun getDashboardDistributor(): DataResult<DashboardResponse, String> {

        return try {
            val token = tokenManager.getToken().first()
            val data = distributorDatasource.getDashboardDistributor(token)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.cause.toString(), e.message.toString())
        }
    }

    override suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailPesananMasukDistributorResponse, String> {
        return try {
            val token = tokenManager.getToken().first()
            val data = distributorDatasource.getDetailPesananMasuk(token, idOrder = idOrder)
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.cause.toString(), e.message.toString())
        }
    }

    override fun getRiwayatOrder(): Flow<PagingData<RiwayatOrderDistributorDataItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = RiwayatRestockPagingSource.RIWAYAT_RESTOCK_PAGE_SIZE,
                pageSize = RiwayatRestockPagingSource.RIWAYAT_RESTOCK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RiwayatOrderPagingSource(
                    dataSource = distributorDatasource,
                    tokenManager
                )
            }
        ).flow
    }

    override fun getUserDistributorSession(): Flow<UserSession> = combine(
    tokenManager.userRoleFlow(),
    tokenManager.getToken()
    ) { role, token ->
        UserSession(role, token)
    }


    override suspend fun logout() {
        tokenManager.removeToken()
    }

//    override suspend fun getPesananMasukDistributor(): Flow<PagingData<RiwayatOrderDistributorDataItem>> {
//        return Pager(
//            config = PagingConfig(
//                initialLoadSize = PesananMasukDistributor.PESANAN_MASUK_PAGE_SIZE,
//                pageSize = PesananMasukPagingSource.PESANAN_MASUK_PAGE_SIZE,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { PesananMasukPagingSource(pabrikDatasource, tokenManager) }).flow
//    }


}