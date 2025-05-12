package com.polije.sosrobahufactoryapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.SalesDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging.ListTokoSalesPagingSource
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging.OrderSalesPagingSource
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListSalesDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDataItem
import com.polije.sosrobahufactoryapp.domain.repository.sales.SalesRepository
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class SalesRepositoryImpl(
    private val salesDataSource: SalesDatasource,
    private val sessionManager: SessionManager
) : SalesRepository {
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
                enablePlaceholders = false
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
                enablePlaceholders = false
            ), pagingSourceFactory = {
                OrderSalesPagingSource(salesDataSource, sessionManager)
            }).flow
    }

    override fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean> =
        sessionManager.isLoggedIn(requiredRole)

    override suspend fun logout() {
        sessionManager.clearSession()
    }

}