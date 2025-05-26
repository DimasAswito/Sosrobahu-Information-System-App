package com.polije.sosrobahufactoryapp.domain.repository.agen

import android.net.Uri
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.GetBarangTerbaruDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.InsertOrderAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangPengaturanHargaAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenDataItem
import com.polije.sosrobahufactoryapp.data.model.agen.TambahBarangTerbaruAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.UpdateBarangPengaturanHargaAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.UpdateStatusOrderAgenResponse
import com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen.SelectedProdukAgen
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

interface AgenRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>
    suspend fun getDashboardAgen(): DataResult<DashboardAgenResponse, HttpErrorCode>

    fun getPesananMasukAgen(): Flow<PagingData<PesananMasukAgenDataItem>>
    suspend fun getDetailPesananMasukAgen(idOrder: Int): DataResult<DetailPesananMasukAgenResponse, HttpErrorCode>
    suspend fun updateStatusPesanan(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateStatusOrderAgenResponse, HttpErrorCode>

    fun getRiwayatOrderAgen(): Flow<PagingData<RiwayatOrderAgenDataItem>>
    suspend fun pilihBarangAgen(): DataResult<PilihBarangDistributorAgenResponse, HttpErrorCode>


    suspend fun downloadNota(idNota: Int): Long

    suspend fun getBarangPengaturanHarga(): DataResult<PilihBarangPengaturanHargaAgenResponse, HttpErrorCode>

    suspend fun getBarangTerbaru(): DataResult<GetBarangTerbaruDistributorAgenResponse, HttpErrorCode>

    suspend fun uploadBarangTerbaru(ids: List<Int>): DataResult<TambahBarangTerbaruAgenResponse, HttpErrorCode>

    suspend fun updateBarangHarga(
        id: Int,
        newPrice: Int
    ): DataResult<UpdateBarangPengaturanHargaAgenResponse, HttpErrorCode>

    suspend fun orderBarang(
        products: List<SelectedProdukAgen>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<InsertOrderAgenResponse, HttpErrorCode>

    fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean>
    suspend fun logout()
}