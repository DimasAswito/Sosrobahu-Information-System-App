package com.polije.sosrobahufactoryapp.ui.factory.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.polije.sosrobahufactoryapp.api.ApiConfig
import com.polije.sosrobahufactoryapp.model.DashboardPabrikResponse
import com.polije.sosrobahufactoryapp.model.PesananPerBulan
import com.polije.sosrobahufactoryapp.model.TopProduct
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _dashboardPabrik = MutableLiveData<DashboardPabrikResponse?>()
    val dashboardPabrik: LiveData<DashboardPabrikResponse?> = _dashboardPabrik

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _pendapatanBulanan = MutableLiveData<Map<String, Float>>()
    val pendapatanBulanan: LiveData<Map<String, Float>> = _pendapatanBulanan

    private val _topProduct = MutableLiveData<TopProduct?>()
    val topProduct: LiveData<TopProduct?> = _topProduct

    init {
        getDashboardPabrik()
    }

    fun getDashboardPabrik() {
        val token = getToken()
        if (token == null) {
            _errorMessage.postValue("Token tidak ditemukan, silakan login ulang.")
            return
        }

        viewModelScope.launch {
            try {
                val response = ApiConfig.instance.getDashboardPabrik("Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { dashboardData ->
                        _dashboardPabrik.postValue(dashboardData)
                        processPendapatanBulanan(dashboardData.pesananPerbulan)
                        processTopProductData(
                            dashboardData.topProductName,
                            dashboardData.namaRokokList,
                            dashboardData.gambarRokokList,
                            dashboardData.totalProdukList
                        )
                    }
                } else {
                    _errorMessage.postValue(
                        "Gagal memuat data dashboard: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error: ${e.message}")
            }
        }
    }

    fun processPendapatanBulanan(pesananPerbulan: Map<String, PesananPerBulan>) {
        val monthlyRevenue = mutableMapOf<String, Float>()
        val allMonths = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)

        for (i in 0..currentMonthIndex) {
            monthlyRevenue[allMonths[i]] = 0f
        }

        for ((date, data) in pesananPerbulan) {
            val monthIndex = date.substring(5, 7).toInt() - 1
            if (monthIndex <= currentMonthIndex) {
                val monthLabel = allMonths[monthIndex]
                monthlyRevenue[monthLabel] = data.total_omset.toFloat()
            }
        }

        _pendapatanBulanan.postValue(monthlyRevenue)
    }

    fun processTopProductData(
        topProductName: String,
        namaRokokList: List<String>,
        gambarRokokList: List<String>,
        totalProdukList: List<Int>
    ) {
        val index = namaRokokList.indexOf(topProductName)
        if (index != -1) {
            _topProduct.postValue(
                TopProduct(
                    name = topProductName,
                    image = gambarRokokList[index],
                    stock = totalProdukList[index]
                )
            )
        } else {
            _errorMessage.postValue("Produk terlaris tidak ditemukan dalam daftar.")
        }
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }
}


