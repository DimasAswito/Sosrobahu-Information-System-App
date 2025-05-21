package com.polije.sosrobahufactoryapp.domain.repository.sales

import android.net.Uri
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DeleteTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.KunjunganTokoDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListSalesDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDataItem
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.TambahKunjunganTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoRequest
import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoResponse
import com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales.SelectedProdukSales
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

interface SalesRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>

    suspend fun getDashboardSales(): DataResult<DashboardSalesResponse, HttpErrorCode>

    fun getListTokoSales(): Flow<PagingData<ListSalesDataItem>>

    fun getOrderSales(): Flow<PagingData<OrderSalesDataItem>>

    fun getKunjunganToko(idToko : Int) : Flow<PagingData<KunjunganTokoDataItem>>

    suspend fun pilihBarangAgen(): DataResult<ListBarangAgenSalesResponse, HttpErrorCode>

    suspend fun orderBarang(
        products: List<SelectedProdukSales>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<OrderSalesResponse, HttpErrorCode>

    suspend fun tambahToko(request: TambahTokoRequest): DataResult<TambahTokoResponse, HttpErrorCode>
    suspend fun updateToko(
        idToko: Int,
        request: TambahTokoRequest
    ): DataResult<TambahTokoResponse, HttpErrorCode>

    suspend fun deleteToko(idToko: Int): DataResult<DeleteTokoResponse, HttpErrorCode>

    suspend fun downloadNota(idNota : Int) : Long

    suspend fun insertKunjunganToko(idToko : Int,tanggal : String, buktiKunjungan : Uri,sisaProduk : Int) : DataResult<TambahKunjunganTokoResponse, HttpErrorCode>


    fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean>
    suspend fun logout()

}