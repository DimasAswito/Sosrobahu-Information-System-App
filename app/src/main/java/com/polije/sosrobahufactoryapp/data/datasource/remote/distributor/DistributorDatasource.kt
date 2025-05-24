package com.polije.sosrobahufactoryapp.data.datasource.remote.distributor

import DashboardDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.GetBarangTerbaruPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.NewBarangRequest
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPengaturanHargaResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PriceUpdateRequest
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.TambahBarangTerbaruDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateBarangPengaturanHargaDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusOrderDistributor
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface DistributorDatasource {

    @POST("distributor/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("distributor/dashboard")
    suspend fun getDashboardDistributor(@Header("Authorization") token: String): DashboardDistributorResponse

    @GET("distributor/pesananMasuk")
    suspend fun getPesananMasuk(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): PesananMasukDistributorResponse

    @GET("distributor/pesananMasuk/{id}")
    suspend fun getDetailPesananMasuk(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
    ): DetailPesananMasukDistributorResponse


    @Multipart
    @POST("distributor/order")
    suspend fun placeOrder(
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part paymentProof: MultipartBody.Part,
        @Header("Authorization") token: String
    ): OrderDistributorResponse

    @GET("distributor/riwayatOrder")
    suspend fun getRiwayatOrder(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): RiwayatOrderDistributorResponse

    @GET("distributor/pilihBarang")
    suspend fun getListBarangPabrik(@Header("Authorization") token: String): PilihBarangPabrikDistributorResponse

    @POST("distributor/pesananMasuk/{id}")
    suspend fun updateDetailPesanan(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
        @Body status: UpdateDetailPesananRequest
    ): UpdateStatusOrderDistributor

    @GET("distributor/pengaturan-harga")
    suspend fun getBarangPengaturanHarga(@Header("Authorization") token: String): PilihBarangPengaturanHargaResponse

    @GET("distributor/barang-baru")
    suspend fun getBarangTerbaru(@Header("Authorization") token: String): GetBarangTerbaruPabrikDistributorResponse

    @PUT("distributor/pengaturan-harga/{id}")
    suspend fun updateBarangPengaturanHarga(
        @Path("id") id: Int,
        @Body body: PriceUpdateRequest, @Header("Authorization") token: String
    ): UpdateBarangPengaturanHargaDistributorResponse

    @POST("distributor/barang-baru")
    suspend fun uploadProducts(
        @Header("Authorization") token: String,
        @Body body: NewBarangRequest
    ): TambahBarangTerbaruDistributorResponse
}