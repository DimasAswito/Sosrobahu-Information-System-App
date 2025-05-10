package com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.SalesDatasource
import com.polije.sosrobahufactoryapp.data.model.sales.ListTokoSalesDataItem
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class ListTokoSalesPagingSource(
    private val dataSource: SalesDatasource,
    private val sessionManager: SessionManager
) : PagingSource<Int, ListTokoSalesDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListTokoSalesDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListTokoSalesDataItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val token = sessionManager.sessionFlow.first().token
            val response = dataSource.getListToko(token = "Bearer $token", page = pageIndex)
            val listToko = response.stores
            Log.d("PagingDebug", "Page: $pageIndex, Data Count: ${response.stores.data.size}")

            LoadResult.Page(
                data = listToko.data,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (response.stores.nextPageUrl != null) pageIndex + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val LIST_TOKO_SALES_PAGE_SIZE = 10
    }
}