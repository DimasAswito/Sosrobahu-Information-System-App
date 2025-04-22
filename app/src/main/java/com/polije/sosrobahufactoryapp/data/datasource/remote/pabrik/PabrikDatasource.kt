package com.polije.sosrobahufactoryapp.data.datasource.remote.pabrik

import DashboardResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.DetailOrderResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.InsertRestockResponse
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.pabrik.RiwayatRestockResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PabrikDatasource {
    @POST("pabrik/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("pabrik/dashboard")
    suspend fun getDashboardPabrik(@Header("Authorization") token: String): DashboardResponse

    @GET("pabrik/pesananMasuk")
    suspend fun getPesananMasuk(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): PesananMasukResponse

    @GET("pabrik/riwayatPabrik")
    suspend fun getRiwayatStokPabrik(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("search") search: String = ""
    ): RiwayatRestockResponse

    @GET("pabrik/pesananMasuk/{id}")
    suspend fun getDetailPesananMasuk(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
    ): DetailOrderResponse

    @GET("pabrik/restock")
    suspend fun getRestockItem(@Header("Authorization") token: String): ProdukRestok

    @POST("pabrik/restock")
    @JvmSuppressWildcards
    suspend fun insertRestock(
        @Header("Authorization") token: String,
        @Body orders: Map<String, Map<String, Int>>
    ): InsertRestockResponse

    @POST("pabrik/pesananMasuk/{id}")
    suspend fun updateDetailPesanan(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
        @Body status: UpdateDetailPesananRequest
    ): UpdateDetailPesananResponse

}