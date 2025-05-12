package com.polije.sosrobahufactoryapp.data.datasource.remote.sales

import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListTokoSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.RiwayatOrderSalesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SalesDatasource {
    @POST("sales/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("sales/dashboard")
    suspend fun getDashboardSales(@Header("Authorization") token: String): DashboardSalesResponse

    @GET("sales/listToko")
    suspend fun getListToko(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): ListTokoSalesResponse

    @GET("sales/riwayatOrder")
    suspend fun getOrderSales(
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): RiwayatOrderSalesResponse

    @GET("sales/kunjungan/{id}")
    suspend fun getKunjungan(@Path("id") idToko: Int, @Header("Authorization") token: String)

}