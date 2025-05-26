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
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.SalesDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging.KunjunganTokoPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging.ListTokoSalesPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging.OrderSalesPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.QuantityItem
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DeleteKunjunganResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DeleteTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.KunjunganTokoDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListSalesDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.TambahKunjunganTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoRequest
import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoResponse
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository
import com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales.SelectedProdukSales
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import com.polije.sosrobahufactoryapp.utils.createKunjunganParts
import com.polije.sosrobahufactoryapp.utils.createOrderSalesParts
import com.polije.sosrobahufactoryapp.utils.toMultipartPart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class SalesRepositoryImpl(
    private val appContext: Context,
    private val salesDataSource: SalesDatasource,
    private val sessionManager: SessionManager
) : SalesRepository {

    private val downloadManager = appContext.getSystemService(DownloadManager::class.java)

    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {
        val request = LoginRequest(username, password)
        return try {
            val data = salesDataSource.login(request)
            sessionManager.saveSession(
                data.token?.plainTextToken ?: "",
                UserRole.SALES,
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

    override suspend fun getDashboardSales(): DataResult<DashboardSalesResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = salesDataSource.getDashboardSales("Bearer $token")
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override fun getListTokoSales(): Flow<PagingData<ListSalesDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = ListTokoSalesPagingSource.LIST_TOKO_SALES_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ListTokoSalesPagingSource.LIST_TOKO_SALES_PAGE_SIZE
            ),
            pagingSourceFactory = {
                ListTokoSalesPagingSource(salesDataSource, sessionManager)
            }
        ).flow
    }

    override fun getOrderSales(): Flow<PagingData<OrderSalesDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = OrderSalesPagingSource.ORDER_AGEN_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = OrderSalesPagingSource.ORDER_AGEN_PAGE_SIZE
            ), pagingSourceFactory = {
                OrderSalesPagingSource(salesDataSource, sessionManager)
            }).flow
    }

    override fun getKunjunganToko(idToko: Int): Flow<PagingData<KunjunganTokoDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = KunjunganTokoPagingSource.LIST_KUNJUNGAN_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = KunjunganTokoPagingSource.LIST_KUNJUNGAN_PAGE_SIZE
            ), pagingSourceFactory = {
                KunjunganTokoPagingSource(idToko, salesDataSource, sessionManager)
            }
        ).flow
    }

    override suspend fun pilihBarangAgen(): DataResult<ListBarangAgenSalesResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = salesDataSource.getListBarangOrder("Bearer $token")
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun orderBarang(
        products: List<SelectedProdukSales>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<OrderSalesResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token

            val items = products
                .filter { (it.quantity ?: 0) > 0 }
                .map { QuantityItem(it.item.idBarangAgen, it.quantity ?: 0) }

            val partMap = createOrderSalesParts(
                totalItems = items.sumOf { it.quantity },
                totalAmount = totalAmount,
                quantities = items
            )

            val paymentProof = buktiUri.toMultipartPart(context = appContext, "payment_proof")
            val data =
                salesDataSource.placeOrder(partMap, paymentProof, "Bearer $token")
            DataResult.Success(data)
        } catch (e: HttpException) {
            val message = e.message
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

    override suspend fun tambahToko(request: TambahTokoRequest): DataResult<TambahTokoResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = salesDataSource.tambahToko("Bearer $token", request)
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun updateToko(
        idToko: Int,
        request: TambahTokoRequest
    ): DataResult<TambahTokoResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = salesDataSource.updateToko(idToko, "Bearer $token", request)
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun deleteToko(idToko: Int): DataResult<DeleteTokoResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = salesDataSource.deleteToko(idToko, "Bearer $token")
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun downloadNota(idNota: Int): Long {
        val request =
            DownloadManager.Request((BuildConfig.BASE_URL + "sales/nota-sales/" + idNota.toString() + "/pdf").toUri())
                .setTitle("Download Nota Sales")
                .setMimeType("application/pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("Nota Sales $idNota.pdf")
                .addRequestHeader(
                    "Authorization",
                    "Bearer ${sessionManager.sessionFlow.first().token}"
                )
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOCUMENTS,
                    "Nota Sales $idNota.pdf"
                )

        return downloadManager.enqueue(request)
    }

    override suspend fun insertKunjunganToko(
        idToko: Int,
        tanggal: String,
        buktiKunjungan: Uri,
        sisaProduk: Int
    ): DataResult<TambahKunjunganTokoResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token ?: ""
            val buktiKunjungan = buktiKunjungan.toMultipartPart(appContext, "gambar")

            val part = createKunjunganParts(tanggal, sisaProduk)

            val data = salesDataSource.insertKunjungan(
                token = "Bearer $token",
                idToko = idToko,
                parts = part,
                photo = buktiKunjungan
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override suspend fun deleteKunjunganToko(idKunjunganToko: Int): DataResult<DeleteKunjunganResponse, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token ?: ""

            val data = salesDataSource.deleteKunjungan(
                token = "Bearer $token",
                idToko = idKunjunganToko,
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
            val error = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean> =
        sessionManager.isLoggedIn(requiredRole)

    override suspend fun logout() {
        sessionManager.clearSession()
    }

}