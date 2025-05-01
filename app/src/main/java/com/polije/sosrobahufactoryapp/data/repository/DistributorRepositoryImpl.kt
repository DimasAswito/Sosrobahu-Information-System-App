package com.polije.sosrobahufactoryapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.DistributorDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.PesananMasukPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DashboardDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class DistributorRepositoryImpl(
    val distributorDatasource: DistributorDatasource,
    val sessionManager: SessionManager
) :
    DistributorRepository {
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {
        val request = LoginRequest(username, password)
        return try {
            val data = distributorDatasource.login(request)
            sessionManager.saveSession(data.token?.plainTextToken ?: "", UserRole.DISTRIBUTOR)
            DataResult.Success(data)
        } catch (e: HttpException) {
            val code = e.code()
            val httpError = HttpErrorCode.entries
                .find { it.code == code }
                ?: HttpErrorCode.UNKNOWN
            DataResult.Error(httpError)
        } catch (_: IOException) {
            DataResult.Error(HttpErrorCode.TIMEOUT)
        } catch (_: Exception) {
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun getDashboardDistributor(): DataResult<DashboardDistributorResponse, HttpErrorCode> {

        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = distributorDatasource.getDashboardDistributor("Bearer $token")
            DataResult.Success(data)
        } catch (e: HttpException) {
            val code = e.code()
            val httpError = HttpErrorCode.entries
                .find { it.code == code }
                ?: HttpErrorCode.UNKNOWN
            DataResult.Error(httpError)
        } catch (_: IOException) {
            DataResult.Error(HttpErrorCode.TIMEOUT)
        } catch (_: Exception) {
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailPesananMasukDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.getDetailPesananMasuk("Bearer $token", idOrder = idOrder)
            DataResult.Success(data)
        } catch (e: HttpException) {
            val code = e.code()
            val httpError = HttpErrorCode.entries
                .find { it.code == code }
                ?: HttpErrorCode.UNKNOWN
            DataResult.Error(httpError)
        } catch (_: IOException) {
            DataResult.Error(HttpErrorCode.TIMEOUT)
        } catch (_: Exception) {
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override fun getPesananMasukDistributor(): Flow<PagingData<PesananMasukDistributorDataItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = PesananMasukPagingSource.RIWAYAT_ORDER_PAGE_SIZE,
                pageSize = PesananMasukPagingSource.RIWAYAT_ORDER_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PesananMasukPagingSource(
                    dataSource = distributorDatasource,
                    sessionManager
                )
            }
        ).flow

    }

    override fun getUserDistributorSession(): Flow<UserSession> = sessionManager.sessionFlow
    override fun isUserIsLogged(): Flow<Boolean> = sessionManager.isLoggedIn

    override suspend fun logout() {
        sessionManager.clearSession()
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