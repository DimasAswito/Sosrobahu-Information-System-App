package com.polije.sosrobahufactoryapp.data.datasource.remote.agen.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.agen.AgenDatasource
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenDataItem
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class PesananMasukAgenPagingDataSource(
    val dataSource: AgenDatasource,
    private val sessionManager: SessionManager
) : PagingSource<Int, PesananMasukAgenDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, PesananMasukAgenDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PesananMasukAgenDataItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val token = sessionManager.sessionFlow.first().token
            val response = dataSource.getPesananMasuk(token = "Bearer $token", page = pageIndex)
            val pesananMasuk = response.data
            Log.d("PagingDebug", "Page: $pageIndex, Data Count: ${response.data.size}")

            LoadResult.Page(
                data = pesananMasuk,
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
        const val PESANAN_MASUK_AGEN_PAGE_SIZE = 10
    }
}