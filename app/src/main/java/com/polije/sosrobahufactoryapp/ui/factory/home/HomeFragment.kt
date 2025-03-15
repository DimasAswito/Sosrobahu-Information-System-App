package com.polije.sosrobahufactoryapp.ui.factory.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.R.id
import com.polije.sosrobahufactoryapp.toRupiah
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeBinding
import com.polije.sosrobahufactoryapp.ui.factory.FactoryViewModel
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginActivity
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginViewModel
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FactoryLoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), FactoryViewModel.Factory(requireContext()))
            .get(FactoryLoginViewModel::class.java)

        calculateStock()
        calculateRevenue()
        countDistributors()
        setupBarChartPendapatan()
        displayTopSellingProduct()

        // Pastikan logoutPabrikButton digunakan setelah onViewCreated()
        binding.logoutPabrikButton.setOnClickListener {
            viewModel.logout()
            Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }

        binding.tvlihatProdukTerlaris.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_topProductFragment)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), FactoryLoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    //    untuk bagian total stok tersedia
    private fun calculateStock() {
        val restockData = resources.getStringArray(R.array.restock_detail_pabrik)
        val orderData = resources.getStringArray(R.array.order_detail_distributor)

        // Hitung total stok masuk dari restock_detail_pabrik
        val totalStock = restockData.sumOf { it.split(",")[4].toInt() }

        // Hitung total pesanan yang sudah selesai (status 1)
        val completedOrders =
            orderData.filter { it.split(",")[6].toInt() == 1 }.sumOf { it.split(",")[5].toInt() }

        // Hitung stok akhir
        val finalStock = totalStock - completedOrders

        // Tampilkan hasilnya di TextView
        binding.stokTersedia.text = "$finalStock karton"
    }

    //    Untuk bagian omset keseluruhan
    private fun calculateRevenue() {
        val orderData = resources.getStringArray(R.array.order_distributor)

        // Hitung total omzet dari pesanan yang sudah selesai (status = 1)
        val totalRevenue =
            orderData.filter { it.split(",")[6].toInt() == 1 } // Filter status 1 (Selesai)
                .sumOf { it.split(",")[3].toInt() } // Menjumlahkan kolom total

        // Tampilkan hasilnya di TextView
        binding.omsetBulan.text = totalRevenue.toRupiah()
    }

    //    Untuk bagian total user distributor
    private fun countDistributors() {
        val distributorData = resources.getStringArray(R.array.user_distributor)

        // Hitung jumlah distributor dari array
        val totalDistributors = distributorData.size

        // Tampilkan hasilnya di TextView
        binding.jumlahDistributor.text = "$totalDistributors Distributor"
    }

    //    Untuk bagian BarChart Pendapatan Keseluruhan
    private fun setupBarChartPendapatan() {
        val orderData = resources.getStringArray(R.array.order_distributor)

        // Daftar bulan dalam urutan tetap
        val allMonths = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        // Dapatkan bulan saat ini
        val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH) // 0-based index

        // Filter hanya bulan dari Januari hingga bulan saat ini
        val months = allMonths.sliceArray(0..currentMonthIndex)

        // Inisialisasi map dengan nilai default 0 agar bulan selalu muncul
        val monthlyRevenue = mutableMapOf<String, Float>().apply {
            months.forEach { this[it] = 0f }
        }

        // Loop melalui data order
        for (order in orderData) {
            val splitData = order.split(",")
            val total = splitData[3].toFloat()
            val status = splitData[6].toInt()
            val date = splitData[4] // Format yyyy-MM-dd

            if (status == 1) { // Hanya pesanan dengan status selesai
                val monthIndex = date.substring(5, 7).toInt() - 1 // Ambil bulan (0-based index)
                if (monthIndex <= currentMonthIndex) { // Hanya sampai bulan saat ini
                    val monthLabel = allMonths[monthIndex] // Konversi ke nama bulan

                    // Tambahkan pendapatan ke bulan yang sesuai
                    monthlyRevenue[monthLabel] = (monthlyRevenue[monthLabel] ?: 0f) + total
                }
            }
        }

        // Buat daftar entry hanya untuk bulan yang muncul sampai bulan saat ini
        val barEntries = months.mapIndexed { index, month ->
            BarEntry(index.toFloat(), monthlyRevenue[month] ?: 0f)
        }

        val barDataSet = BarDataSet(barEntries, "Pendapatan Bulanan (Rp)")
        barDataSet.color = Color.parseColor("#E4C36C")
        barDataSet.setDrawValues(false)

        val barData = BarData(barDataSet)
        binding.barChartPendapatanBulanan.data = barData

        // **Format sumbu X dengan nama bulan (hanya sampai bulan saat ini)**
        binding.barChartPendapatanBulanan.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(months.toList())
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false) // Hilangkan garis vertikal
        }

        binding.barChartPendapatanBulanan.axisRight.isEnabled = false

        // **Hilangkan garis horizontal di belakang grafik**
        binding.barChartPendapatanBulanan.axisLeft.apply {
            setDrawGridLines(false) // Hilangkan garis horizontal
        }

        binding.barChartPendapatanBulanan.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }

        binding.barChartPendapatanBulanan.description.isEnabled = false
        binding.barChartPendapatanBulanan.invalidate() // Refresh Chart
    }

    //    Untuk bagian Produk Terlaris
    private fun displayTopSellingProduct() {
        val orderDetailData = resources.getStringArray(R.array.order_detail_distributor)
        val productData = resources.getStringArray(R.array.master_barang)

        if (orderDetailData.isEmpty() || productData.isEmpty()) {
            Log.e("TopProduct", "Data order atau master barang kosong!")
            return
        }

        // Buat daftar ID produk yang valid dari master_barang
        val validProductIds = productData.mapNotNull {
            it.split(",").getOrNull(0)?.toIntOrNull()
        }.toSet()

        val productRevenueMap = mutableMapOf<Int, Int>()

        for (order in orderDetailData) {
            val splitData = order.split(",")
            if (splitData.size < 7) {
                Log.e("TopProduct", "Format data order_detail_distributor salah: $order")
                continue
            }

            val productId = splitData[4].toIntOrNull()
            val totalPrice = splitData[6].toIntOrNull()

            // Cek apakah ID produk valid
            if (productId != null && totalPrice != null && productId in validProductIds) {
                productRevenueMap[productId] = (productRevenueMap[productId] ?: 0) + totalPrice
            } else {
                Log.w(
                    "TopProduct",
                    "Produk dengan ID $productId tidak valid atau tidak ditemukan di master_barang"
                )
            }
        }

        // Cari produk dengan pendapatan tertinggi
        val topProductId = productRevenueMap.maxByOrNull { it.value }?.key

        if (topProductId != null) {
            val topProduct = productData.firstOrNull {
                val splitProduct = it.split(",")
                splitProduct.size >= 4 && splitProduct[0].toIntOrNull() == topProductId
            }

            if (topProduct != null) {
                val splitProduct = topProduct.split(",")
                val productName = splitProduct[1]
                val productImage = splitProduct[3] // Nama gambar di drawable
                val totalRevenue = productRevenueMap[topProductId]

                // Set data ke UI
                binding.topProductName.text = productName
                binding.topProductRevenue.text = totalRevenue?.toRupiah()

                // Load gambar dari drawable
                val imageResId =
                    resources.getIdentifier(productImage, "drawable", requireContext().packageName)
                if (imageResId != 0) {
                    binding.topProductImage.setImageResource(imageResId)
                } else {
                    Log.e("TopProduct", "Gambar $productImage tidak ditemukan di drawable")
                }
            }
        } else {
            Log.w("TopProduct", "Tidak ada produk terlaris ditemukan")
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


