package com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.SalesDatasource
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDataItem
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class OrderSalesPagingSource(
    private val dataSource: SalesDatasource,
    private val sessionManager: SessionManager
) : PagingSource<Int, OrderSalesDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, OrderSalesDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderSalesDataItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val token = sessionManager.sessionFlow.first().token
            val response = dataSource.getOrderSales(token = "Bearer $token", page = pageIndex)

            Log.d("PagingDebug", "Page: $pageIndex, Data Count: ${response.data.size}")

            LoadResult.Page(
                data = response.data,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (response.nextPageUrl != null) pageIndex + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val ORDER_AGEN_PAGE_SIZE = 10
    }
}