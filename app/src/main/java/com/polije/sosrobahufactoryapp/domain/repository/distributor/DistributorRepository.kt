package com.polije.sosrobahufactoryapp.domain.repository.distributor

import DashboardDistributorResponse
import android.net.Uri
import androidx.paging.PagingData
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.GetBarangTerbaruPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPengaturanHargaResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.data.model.distributor.TambahBarangTerbaruDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateBarangPengaturanHargaDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusOrderDistributor
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import com.polije.sosrobahufactoryapp.utils.DataResult
import com.polije.sosrobahufactoryapp.utils.HttpErrorCode
import com.polije.sosrobahufactoryapp.utils.UserRole
import kotlinx.coroutines.flow.Flow

interface DistributorRepository {
    suspend fun login(username: String, password: String): DataResult<LoginResponse, HttpErrorCode>

    suspend fun getDashboardDistributor(): DataResult<DashboardDistributorResponse, HttpErrorCode>

    suspend fun getDetailPesananMasuk(idOrder: Int): DataResult<DetailPesananMasukDistributorResponse, HttpErrorCode>

    fun getPesananMasukDistributor(): Flow<PagingData<PesananMasukDistributorDataItem>>

    fun getRiwayatOrderDistributor(): Flow<PagingData<RiwayatOrderDistributorDataItem>>

    suspend fun pilihBarangPabrik(): DataResult<PilihBarangPabrikDistributorResponse, HttpErrorCode>

    suspend fun orderBarang(
        products: List<SelectedProdukDistributor>,
        totalAmount: Int,
        buktiUri: Uri
    ): DataResult<OrderDistributorResponse, HttpErrorCode>

    suspend fun updateStatusPesanan(
        idOrder: Int,
        status: Int
    ): DataResult<UpdateStatusOrderDistributor, HttpErrorCode>

    suspend fun getBarangPengaturanHarga(): DataResult<PilihBarangPengaturanHargaResponse, HttpErrorCode>

    suspend fun getBarangTerbaru(): DataResult<GetBarangTerbaruPabrikDistributorResponse, HttpErrorCode>

    suspend fun uploadBarangTerbaru(ids: List<Int>): DataResult<TambahBarangTerbaruDistributorResponse, HttpErrorCode>

    suspend fun updateBarangHarga(
        id: Int,
        newPrice: Int
    ): DataResult<UpdateBarangPengaturanHargaDistributorResponse, HttpErrorCode>

    suspend fun downloadNota(idNota: Int): Long

    fun isUserIsLogged(requiredRole: UserRole): Flow<Boolean>
    suspend fun logout()

}