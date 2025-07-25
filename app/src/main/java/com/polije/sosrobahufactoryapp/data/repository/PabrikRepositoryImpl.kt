package com.polije.sosrobahufactoryapp.data.repository

import DashboardResponse
import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.pabrik.PabrikDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.pabrik.paging.PesananMasukPabrikPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.pabrik.paging.RiwayatRestockPabrikPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderPabrikResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.pabrik.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananResponse
import com.polije.sosrobahufactoryapp.domain.repository.pabrik.PabrikRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class PabrikRepositoryImpl(
    private val context: Context,
    private val pabrikDatasource: PabrikDatasource,
    private val sessionManager: SessionManager
) :
    PabrikRepository {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override suspend fun login(
        username: String,
        password: String
    ): DataResult<LoginResponse, HttpErrorCode> {

        val request = LoginRequest(username, password)
        return try {
            val data = pabrikDatasource.login(request)
            sessionManager.saveSession(
                token = data.token?.plainTextToken ?: "",
                UserRole.PABRIK,
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

    override suspend fun getDashboardPabrik(): DataResult<DashboardResponse, HttpErrorCode> {

        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = pabrikDatasource.getDashboardPabrik("Bearer $token")
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

    override suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailOrderPabrikResponse, HttpErrorCode> {
        return try {
            val token = "Bearer ${sessionManager.sessionFlow.first().token}"
            val data = pabrikDatasource.getDetailPesananMasuk(token, idOrder)
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

    override suspend fun updateDetailPesananMasuk(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateDetailPesananResponse, HttpErrorCode> {
        return try {
            val token = "Bearer ${sessionManager.sessionFlow.first().token}"
            val data = pabrikDatasource.updateDetailPesanan(
                token = token,
                idOrder,
                status = UpdateDetailPesananRequest(status)
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
            val message = e.message
            DataResult.Error(HttpErrorCode.UNKNOWN)
        }
    }

    override fun getPesananMasuk(): Flow<PagingData<PesananMasukItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = PesananMasukPabrikPagingSource.PESANAN_MASUK_PABRIK_PAGE_SIZE,
                pageSize = PesananMasukPabrikPagingSource.PESANAN_MASUK_PABRIK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PesananMasukPabrikPagingSource(
                    pabrikDatasource,
                    sessionManager
                )
            }).flow


    }

    override fun getRiwayatRestockPabrik(query: String): Flow<PagingData<RiwayatRestockItem>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = RiwayatRestockPabrikPagingSource.RIWAYAT_RESTOCK_PABRIK_PAGE_SIZE,
                pageSize = RiwayatRestockPabrikPagingSource.RIWAYAT_RESTOCK_PABRIK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RiwayatRestockPabrikPagingSource(query, pabrikDatasource, sessionManager)
            }
        ).flow


    }

    override suspend fun downloadNotaPabrik(idNota: Int): Long {
        val request =
            DownloadManager.Request((BuildConfig.BASE_URL + "pabrik/nota-pabrik/" + idNota.toString() + "/pdf").toUri())
                .setTitle("Download Nota Pabrik")
                .setMimeType("application/pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("Nota Pabrik $idNota.pdf")
                .addRequestHeader(
                    "Authorization",
                    "Bearer ${sessionManager.sessionFlow.first().token}"
                )
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, "Nota Pabrik $idNota.pdf")

        return downloadManager.enqueue(request)
    }


    override suspend fun logout() {
        sessionManager.clearSession()
    }

    override fun isUserIsLogged(requiredUser: UserRole): Flow<Boolean> =
        sessionManager.isLoggedIn(requiredUser)

    override suspend fun getItemRestock(): DataResult<ProdukRestok, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = pabrikDatasource.getRestockItem("Bearer $token")
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

    override suspend fun insertRestock(orders: Map<String, Map<String, Int>>): DataResult<Boolean, HttpErrorCode> {
        return try {
            val token = sessionManager.sessionFlow.first().token
            val data = pabrikDatasource.insertRestock("Bearer $token", orders)
            DataResult.Success(data.success)
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
}