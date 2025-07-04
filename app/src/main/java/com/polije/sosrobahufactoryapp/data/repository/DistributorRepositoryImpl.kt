package com.polije.sosrobahufactoryapp.data.repository

import DashboardDistributorResponse
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.DistributorDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.PesananMasukDistributorPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.paging.RiwayatOrderDistributorPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.GetBarangTerbaruPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.NewBarangDistributorRequest
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPengaturanHargaResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PriceUpdateDistributorRequest
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.TambahBarangTerbaruDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateBarangPengaturanHargaDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusOrderDistributor
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import com.polije.sosrobahufactoryapp.domain.repository.distributor.DistributorRepository
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
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

    private val downloadManager = appContext.getSystemService(DownloadManager::class.java)
    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {
        val request = LoginRequest(username, password)
        return try {
            val data = distributorDatasource.login(request)
            sessionManager.saveSession(
                data.token?.plainTextToken ?: "",
                UserRole.DISTRIBUTOR,
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
                initialLoadSize = PesananMasukDistributorPagingSource.PESANAN_MASUK_DISTRIBUTOR_PAGE_SIZE,
                pageSize = PesananMasukDistributorPagingSource.PESANAN_MASUK_DISTRIBUTOR_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PesananMasukDistributorPagingSource(
                    dataSource = distributorDatasource,
                    sessionManager
                )
            }
        ).flow

    }

    override fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean> =
        sessionManager.isLoggedIn(requiredRole)

    override suspend fun logout() {
        sessionManager.clearSession()
    }

    override fun getRiwayatOrderDistributor(): Flow<PagingData<RiwayatOrderDistributorDataItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = RiwayatOrderDistributorPagingSource.RIWAYAT_ORDER_DISTRIBUTOR_PAGE_SIZE,
                pageSize = RiwayatOrderDistributorPagingSource.RIWAYAT_ORDER_DISTRIBUTOR_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RiwayatOrderDistributorPagingSource(
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

    override suspend fun updateStatusPesanan(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateStatusOrderDistributor, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.updateDetailPesanan(
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

    override suspend fun getBarangPengaturanHarga(): DataResult<PilihBarangPengaturanHargaResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.getBarangPengaturanHarga(
                    "Bearer $token"
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

    override suspend fun getBarangTerbaru(): DataResult<GetBarangTerbaruPabrikDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.getBarangTerbaru(
                    "Bearer $token"
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

    override suspend fun uploadBarangTerbaru(ids: List<Int>): DataResult<TambahBarangTerbaruDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.uploadProducts(
                    "Bearer $token",
                    NewBarangDistributorRequest(ids)

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

    override suspend fun updateBarangHarga(
        id: Int,
        newPrice: Int
    ): DataResult<UpdateBarangPengaturanHargaDistributorResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                distributorDatasource.updateBarangPengaturanHarga(
                    id = id,
                    PriceUpdateDistributorRequest(newPrice),
                    "Bearer $token"
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


    override suspend fun downloadNota(idNota: Int): Long {
        val request =
            DownloadManager.Request((BuildConfig.BASE_URL + "distributor/nota-distributor/" + idNota.toString() + "/pdf").toUri())
                .setTitle("Download Nota Distributor")
                .setMimeType("application/pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("Nota Distributor $idNota.pdf")
                .addRequestHeader(
                    "Authorization",
                    "Bearer ${sessionManager.sessionFlow.first().token}"
                )
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOCUMENTS,
                    "Nota Distributor $idNota.pdf"
                )

        return downloadManager.enqueue(request)
    }

}