package com.polije.sosrobahufactoryapp.data.datasource.remote.sales.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.polije.sosrobahufactoryapp.data.datasource.local.SessionManager
import com.polije.sosrobahufactoryapp.data.datasource.remote.sales.SalesDatasource
import com.polije.sosrobahufactoryapp.data.model.sales.KunjunganTokoDataItem
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class KunjunganTokoPagingSource(
    private val idToko: Int,
    private val salesDatasource: SalesDatasource,
    private val sessionManager: SessionManager
) : PagingSource<Int, KunjunganTokoDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, KunjunganTokoDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KunjunganTokoDataItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val token = sessionManager.sessionFlow.first().token
            val response =
                salesDatasource.getKunjungan(idToko, token = "Bearer $token", page = pageIndex)
            val listToko = response.kunjunganToko
            Log.d("PagingDebug", "Page: $pageIndex, Data Count: ${response.kunjunganToko.data.size}")

            LoadResult.Page(
                data = listToko.data,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (response.kunjunganToko.nextPageUrl != null) pageIndex + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val LIST_KUNJUNGAN_PAGE_SIZE = 10
    }
}