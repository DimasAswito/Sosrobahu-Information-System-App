package com.polije.sosrobahufactoryapp.data.pabrik.source.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.polije.sosrobahufactoryapp.data.local.TokenManager
import com.polije.sosrobahufactoryapp.data.model.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.data.pabrik.source.remote.PabrikDatasource
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class RiwayatRestockPagingSource(
    private val query : String,
    private val dataSource: PabrikDatasource,
    private val tokenManager: TokenManager
) : PagingSource<Int, RiwayatRestockItem>() {
    override fun getRefreshKey(state: PagingState<Int, RiwayatRestockItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RiwayatRestockItem> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val token = tokenManager.getToken().first()
            val response = dataSource.getRiwayatStokPabrik(search = query ,token = "Bearer $token", page = pageIndex)
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
        const val RIWAYAT_RESTOCK_PAGE_SIZE = 10
    }

}