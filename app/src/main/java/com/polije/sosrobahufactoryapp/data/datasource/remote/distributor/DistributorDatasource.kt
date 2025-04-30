package com.polije.sosrobahufactoryapp.data.datasource.remote.distributor

import DashboardResponse
import com.polije.sosrobahufactoryapp.data.model.LoginRequest
import com.polije.sosrobahufactoryapp.data.model.LoginResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DashboardDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.DetailPesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.OrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorResponse
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananRequest
import com.polije.sosrobahufactoryapp.data.model.pabrik.UpdateDetailPesananResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
        @Part("total_items") totalItems: RequestBody,
        @Part("total_amount") totalAmount: RequestBody,
        @Part("quantities") quantitiesJson: RequestBody,
        @Part paymentProof: MultipartBody.Part
    ): OrderDistributorResponse

    @GET("distributor/riwayatOrder")
    suspend fun getRiwayatOrder(@Header("Authorization") token: String): RiwayatOrderDistributorResponse

    @POST("distributor/pesananMasuk/{id}")
    suspend fun updateDetailPesanan(
        @Header("Authorization") token: String,
        @Path("id") idOrder: Int,
        @Body status: UpdateDetailPesananRequest
    ): UpdateDetailPesananResponse

}