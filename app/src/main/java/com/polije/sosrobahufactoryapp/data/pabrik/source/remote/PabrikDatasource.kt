package com.polije.sosrobahufactoryapp.data.pabrik.source.remote

import DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.data.model.DetailOrderResponse
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.PesananMasukResopnse
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.RiwayatRestockResponse
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
    suspend fun getDashboardPabrik(@Header("Authorization") token: String): DashboardPabrikResponse

    @GET("pabrik/pesananMasuk")
    suspend fun getPesananMasuk(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ): PesananMasukResopnse

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
    suspend fun getRestockItem(@Header("Authorization") token : String) : ProdukRestok

}