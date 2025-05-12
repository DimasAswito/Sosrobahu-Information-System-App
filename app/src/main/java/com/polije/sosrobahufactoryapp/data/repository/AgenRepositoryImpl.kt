package com.polije.sosrobahufactoryapp.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.AgenDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.paging.PesananMasukAgenPagingDataSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.paging.RiwayatOrderAgenPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.RiwayatOrderDistributorPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusPesananMasukResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen.SelectedProdukAgen
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.createOrderParts
import com.polije.sosrobahufactoryapp.utils.toMultipartPart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class AgenRepositoryImpl(
    private val appContext : Context,
    private val agenDatasource: AgenDatasource,
    private val sessionManager: SessionManager
) : AgenRepository {
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {
        val request = LoginRequest(username, password)
        return try {
            val data = agenDatasource.login(request)
            sessionManager.saveSession(
                data.token?.plainTextToken ?: "",
                UserRole.AGEN,
                data.token?.accessToken?.expiresAt ?: ""
            )
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

    override suspend fun getDashboardAgen(): DataResult<DashboardAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token ?: ""
            val data = agenDatasource.getDashboardAgen("Bearer $token")
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

    override fun getPesananMasukAgen(): Flow<PagingData<PesananMasukAgenDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PesananMasukAgenPagingDataSource.PESANAN_MASUK_AGEN_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PesananMasukAgenPagingDataSource.PESANAN_MASUK_AGEN_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                PesananMasukAgenPagingDataSource(
                    agenDatasource,
                    sessionManager
                )
            }
        ).flow
    }

    override suspend fun getDetailPesananMasukAgen(idOrder: Int): DataResult<DetailPesananMasukAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.getDetailPesananMasuk("Bearer $token", idOrder = idOrder)
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

    override suspend fun updateStatusPesanan(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateStatusPesananMasukResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.updateDetailPesanan(
                    "Bearer $token",
                    idOrder,
                    UpdateDetailPesananRequest(status)
                )
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

    override fun getRiwayatOrderDistributor(): Flow<PagingData<RiwayatOrderAgenDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = RiwayatOrderDistributorPagingSource.RIWAYAT_ORDER_DISTRIBUTOR_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = RiwayatOrderDistributorPagingSource.RIWAYAT_ORDER_DISTRIBUTOR_PAGE_SIZE,
            ), pagingSourceFactory = {
                RiwayatOrderAgenPagingSource(agenDatasource, sessionManager)
            }).flow
    }

    override suspend fun pilihBarangDistributor(): DataResult<PilihBarangDistributorAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.getListBarangDistributor(
                    "Bearer $token",

                    )
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
        products: List<SelectedProdukAgen>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<OrderDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token

            val items = products
                .filter { (it.quantity ?: 0) > 0 }
                .map { QuantityItem(it.item.idBarangDistributor, it.quantity ?: 0) }

            val partMap = createOrderParts(
                totalItems = items.sumOf { it.quantity },
                totalAmount = totalAmount,
                quantities = items
            )

            val paymentProof = buktiUri.toMultipartPart(context = appContext, "payment_proof")
            val data =
                agenDatasource.placeOrder(partMap, paymentProof, "Bearer $token")
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


    override fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean> =
        sessionManager.isLoggedIn(requiredRole)

    override suspend fun logout() {
        sessionManager.clearSession()
    }


}