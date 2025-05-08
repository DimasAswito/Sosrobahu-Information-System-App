package com.polije.sosrobahufactoryapp.data.datasource.remote.agen

import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DashboardAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.DetailPesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.UpdateStatusPesananMasukResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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


}