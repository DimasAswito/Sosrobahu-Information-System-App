package com.polije.sosrobahufactoryapp.data.datasource.remote.agen.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.AgenDatasource
import com.polije.sosrobahufactoryapp.data.datasource.remote.distributor.DistributorDatasource
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenDataItem
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class RiwayatOrderAgenPagingSource(
    private val agenDatasource: AgenDatasource,
    private val sessionManager: SessionManager
) : PagingSource<Int, RiwayatOrderAgenDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, RiwayatOrderAgenDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RiwayatOrderAgenDataItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val token = sessionManager.sessionFlow.first().token
            val response = agenDatasource.getRiwayatOrder( page = pageIndex,token = "Bearer $token")

            Log.d("PagingDebug", "Page: $pageIndex, Data Count: ${response.data.size}")

            LoadResult.Page(
                data = response.data  ,
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
        const val RIWAYAT_ORDER_DISTRIBUTOR_PAGE_SIZE = 10
    }
}