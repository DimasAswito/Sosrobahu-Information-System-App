package com.polije.sosrobahufactoryapp.data.datasource.remote.sales

import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DashboardSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DeleteKunjunganResponse
import com.polije.sosrobahufactoryapp.data.model.sales.DeleteTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.KunjunganTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.ListTokoSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.RiwayatOrderSalesResponse
import com.polije.sosrobahufactoryapp.data.model.sales.TambahKunjunganTokoResponse
import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoRequest
import com.polije.sosrobahufactoryapp.data.model.sales.TambahTokoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface SalesDatasource {
    @POST("sales/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("sales/dashboard")
    suspend fun getDashboardSales(@Header("Authorization") token: String): DashboardSalesResponse

    @GET("sales/listToko")
    suspend fun getListToko(
        @Query("page") page: Int, @Header("Authorization") token: String
    ): ListTokoSalesResponse

    @GET("sales/riwayatOrder")
    suspend fun getOrderSales(
        @Query("page") page: Int, @Header("Authorization") token: String
    ): RiwayatOrderSalesResponse

    @GET("sales/kunjungan/{id}")
    suspend fun getKunjungan(
        @Path("id") idToko: Int,
        @Header("Authorization") token: String,
        @Query("page") page: Int,
    ): KunjunganTokoResponse

    @GET("sales/listBarangOrder")
    suspend fun getListBarangOrder(@Header("Authorization") token: String): ListBarangAgenSalesResponse

    @Multipart
    @POST("sales/order")
    suspend fun placeOrder(
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part paymentProof: MultipartBody.Part,
        @Header("Authorization") token: String

    ): OrderSalesResponse

    @POST("sales/toko")
    suspend fun tambahToko(
        @Header("Authorization") token: String, @Body request: TambahTokoRequest
    ): TambahTokoResponse

    @PUT("sales/toko/{id}")
    suspend fun updateToko(
        @Path("id") idToko: Int,
        @Header("Authorization") token: String,
        @Body request: TambahTokoRequest
    ): TambahTokoResponse

    @DELETE("sales/toko/{id}")
    suspend fun deleteToko(
        @Path("id") idToko: Int,
        @Header("Authorization") token: String,
    ): DeleteTokoResponse

    @Multipart
    @POST("sales/kunjungan/{id_toko}")
    suspend fun insertKunjungan(
        @Header("Authorization") token: String,
        @Path("id_toko") idToko: Int,
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part photo: MultipartBody.Part? = null
    ): TambahKunjunganTokoResponse

    @DELETE("sales/kunjungan/{id_toko}")
    suspend fun deleteKunjungan(
        @Header("Authorization") token: String,
        @Path("id_toko") idToko: Int,
    ): DeleteKunjunganResponse


}