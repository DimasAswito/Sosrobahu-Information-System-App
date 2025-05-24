package com.polije.sosrobahufactoryapp.data.repository

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
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.AgenDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.paging.PesananMasukAgenPagingDataSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.paging.RiwayatOrderAgenPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.GetBarangTerbaruDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.InsertOrderAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.NewBarangAgenRequest
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangPengaturanHargaAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PriceUpdateAgenRequest
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.agen.TambahBarangTerbaruAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.UpdateBarangPengaturanHargaAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.UpdateStatusOrderAgenResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import com.polije.sosrobahufactoryapp.domain.repository.agen.AgenRepository
import com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen.SelectedProdukAgen
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.createOrderPartsAgen
import com.polije.sosrobahufactoryapp.utils.toMultipartPart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class AgenRepositoryImpl(
    private val appContext: Context,
    private val agenDatasource: AgenDatasource,
    private val sessionManager: SessionManager
) : AgenRepository {

    private val downloadManager = appContext.getSystemService(DownloadManager::class.java)

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
            val token = sessionManager.sessionFlow.first().token ?: ""
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
    ): DataResult<UpdateStatusOrderAgenResponse, HttpErrorCode> {
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

    override fun getRiwayatOrderAgen(): Flow<PagingData<RiwayatOrderAgenDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = RiwayatOrderAgenPagingSource.RIWAYAT_ORDER_AGEN_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = RiwayatOrderAgenPagingSource.RIWAYAT_ORDER_AGEN_PAGE_SIZE,
            ), pagingSourceFactory = {
                RiwayatOrderAgenPagingSource(agenDatasource, sessionManager)
            }).flow
    }

    override suspend fun pilihBarangAgen(): DataResult<PilihBarangDistributorAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.getListBarangAgen(
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

    override suspend fun downloadNota(idNota: Int): Long {
        val request =
            DownloadManager.Request((BuildConfig.BASE_URL + "agen/nota-agen/" + idNota.toString() + "/pdf").toUri())
                .setTitle("Download Nota Agen")
                .setMimeType("application/pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("Nota Agen $idNota.pdf")
                .addRequestHeader(
                    "Authorization",
                    "Bearer ${sessionManager.sessionFlow.first().token}"
                )
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOCUMENTS,
                    "Nota Agen $idNota.pdf"
                )

        return downloadManager.enqueue(request)
    }

    override suspend fun getBarangPengaturanHarga(): DataResult<PilihBarangPengaturanHargaAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.getBarangPengaturanHarga(

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

    override suspend fun getBarangTerbaru(): DataResult<GetBarangTerbaruDistributorAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.getBarangTerbaru(

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

    override suspend fun uploadBarangTerbaru(ids: List<Int>): DataResult<TambahBarangTerbaruAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.uploadProducts(
                    "Bearer $token",
                    NewBarangAgenRequest(ids)

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
    ): DataResult<UpdateBarangPengaturanHargaAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data =
                agenDatasource.updateBarangPengaturanHarga(
                    id = id,
                    PriceUpdateAgenRequest(newPrice),
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

    override suspend fun orderBarang(
        products: List<SelectedProdukAgen>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<InsertOrderAgenResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token

            val items = products
                .filter { (it.quantity ?: 0) > 0 }
                .map { QuantityItem(it.item.idBarangDistributor, it.quantity ?: 0) }

            val partMap = createOrderPartsAgen(
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