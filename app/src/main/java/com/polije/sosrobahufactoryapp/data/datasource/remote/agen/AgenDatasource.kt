package com.polije.sosrobahufactoryapp.data.datasource.remote.agen

import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PilihBarangDistributorAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusPesananMasukResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface AgenDatasource {
    @POST("agen/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("agen/dashboard")
    suspend fun getDashboardAgen(@Header("Authorization") token: String): DashboardAgenResponse


    @GET("agen/pesananMasuk")
    suspend fun getPesananMasuk(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): PesananMasukAgenResponse

    @GET("agen/pesananMasuk/{id}")
    suspend fun getDetailPesananMasuk(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
    ): DetailPesananMasukAgenResponse

    @POST("agen/pesananMasuk/{id}")
    suspend fun updateDetailPesanan(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
        @Body status: UpdateDetailPesananRequest
    ): UpdateStatusPesananMasukResponse

    @GET("agen/riwayatOrder")
    suspend fun getRiwayatOrder(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): RiwayatOrderAgenResponse

    @GET("agen/pilihBarang")
    suspend fun getListBarangDistributor(@Header("Authorization") token: String): PilihBarangDistributorAgenResponse

    @Multipart
    @POST("agen/order")
    suspend fun placeOrder(
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part paymentProof: MultipartBody.Part,
        @Header("Authorization") token: String

    ): OrderDistributorResponse



}