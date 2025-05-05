package com.polije.sosrobahufactoryapp.data.repository

import DashboardDistributorResponse
import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.DistributorDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.PesananMasukPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.RiwayatOrderPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.UserSession
import com.polije.sosrobahufactoryapp.utils.createOrderParts
import com.polije.sosrobahufactoryapp.utils.toMultipartPart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class DistributorRepositoryImpl(
    val appContext: Context,
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
                initialLoadSize = PesananMasukPagingSource.PESANAN_MASUK_PAGE_SIZE,
                pageSize = PesananMasukPagingSource.PESANAN_MASUK_PAGE_SIZE,
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

    override fun getRiwayatOrderDistributor(): Flow<PagingData<RiwayatOrderDistributorDataItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = RiwayatOrderPagingSource.PESANAN_MASUK_PAGE_SIZE,
                pageSize = RiwayatOrderPagingSource.PESANAN_MASUK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RiwayatOrderPagingSource(
                    distributorDatasource,
                    sessionManager
                )
            }).flow
    }

    override suspend fun pilihBarangPabrik(): DataResult<PilihBarangPabrikDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.getListBarangPabrik("Bearer $token")
            DataResult.Success(data)
        } catch (e: HttpException) {
            val code = e.code()
            val httpError = HttpErrorCode.entries
                .find { it.code == code }
                ?: HttpErrorCode.UNKNOWN
            DataResult.Error(httpError)
        } catch (_: IOException) {
            DataResult.Error(HttpErrorCode.TIMEOUT)
        } catch (e: Exception) {
            val error = e.message.toString()
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun orderBarang(
        products: List<SelectedProdukDistributor>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<OrderDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token

            val items = products
                .filter { (it.quantity ?: 0) > 0 }
                .map { QuantityItem(it.item.idMasterBarang ?: 0, it.quantity ?: 0) }

            val partMap = createOrderParts(
                totalItems = items.sumOf { it.quantity },
                totalAmount = totalAmount,
                quantities = items
            )

            val paymentProof = buktiUri.toMultipartPart(context = appContext, "payment_proof")
            val data =
                distributorDatasource.placeOrder(partMap, paymentProof, "Bearer $token")
            DataResult.Success(data)
        } catch (e: HttpException) {
            val code = e.code()
            val httpError = HttpErrorCode.entries
                .find { it.code == code }
                ?: HttpErrorCode.UNKNOWN
            DataResult.Error(httpError)
        } catch (_: IOException) {
            DataResult.Error(HttpErrorCode.TIMEOUT)
        } catch (e: Exception) {
            val error = e.message.toString()
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

}