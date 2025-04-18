package com.polije.sosrobahufactoryapp.ui.factory.home

import PesananPerBulan
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.polije.sosrobahufactoryapp.BuildConfig.PICTURE_BASE_URL
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.ListTopSellingProduct
import com.polije.sosrobahufactoryapp.data.model.TopSellingProduct
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeBinding
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginActivity
import com.polije.sosrobahufactoryapp.utils.toRupiah
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    lateinit var listTopSellingProduct: ListTopSellingProduct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.logoutPabrikButton.setOnClickListener {

            Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }

        binding.tvlihatProdukTerlaris.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToTopProductFragment(
                listTopSellingProduct
            )

            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {

            homeViewModel.state.collectLatest { state ->
                when (state) {
                    is HomeState.Failure -> {
                        Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                    HomeState.Initial -> {
                        binding.progressBar2.visibility = View.GONE
                    }


                    HomeState.Loading -> {
                        binding.progressBar2.visibility = View.VISIBLE
                    }

                    is HomeState.Success -> {

                        binding.progressBar2.visibility = View.GONE
                        binding.stokPabrikTersedia.text =
                            getString(R.string.karton, state.dashboardPabrik.finalStockKarton)
                        binding.omsetPabrik.text =
                            Integer.parseInt(state.dashboardPabrik.totalPendapatan).toRupiah()
                        binding.jumlahDistributor.text =
                            getString(R.string.distributor, state.dashboardPabrik.totalDistributor)

                        val topProductName = state.dashboardPabrik.topProductName
                        val namaRokokList = state.dashboardPabrik.namaRokokList
                        val gambarRokokList = state.dashboardPabrik.gambarRokokList
                        val totalProdukList = state.dashboardPabrik.totalProdukList

                        val topProductIndex = namaRokokList.indexOf(topProductName)

                        val combinedList =
                            ListTopSellingProduct(namaRokokList.indices.map { index ->
                                TopSellingProduct(
                                    rank = 0, // Nanti diisi setelah sorting
                                    name = namaRokokList.getOrNull(index)
                                        ?: "Produk Tidak Diketahui",
                                    image = gambarRokokList.getOrNull(index) ?: "",
                                    stock = totalProdukList.getOrNull(index) ?: 0
                                )
                            }.sortedBy { it.stock })// urutkan berdasarkan stok paling sedikit)

                        val rankedList =
                            combinedList.listTopSellingProduct.mapIndexed { index, product ->
                                product.copy(rank = index + 1)
                            }
                        listTopSellingProduct = ListTopSellingProduct(rankedList)


                        if (topProductIndex != -1) {
                            val topProductImageName = gambarRokokList[topProductIndex]
                            val topProductStock = totalProdukList[topProductIndex]

                            binding.topProductName.text = topProductName
                            binding.topProductStock.text =
                                getString(R.string.karton, topProductStock)

                            val imageUrl = PICTURE_BASE_URL + "produk/" + topProductImageName

                            val circularProgressDrawable =
                                CircularProgressDrawable(requireContext()).apply {
                                    strokeWidth = 5f
                                    centerRadius = 30f
                                    start()
                                }

                            Glide.with(requireContext())
                                .load(imageUrl)
                                .placeholder(circularProgressDrawable)
                                .error(R.drawable.rokok)
                                .into(binding.topProductImage)
                        }
                        val pendapatanBulanan = convertToMonthlyRevenueMap(state.pendapatanBulanan)
                        setupBarChartPendapatan(pendapatanBulanan)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), FactoryLoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupBarChartPendapatan(pendapatanMap: Map<String, Float>) {
        val allMonths = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        val currentMonthIndex = Calendar.getInstance().get(Calendar.MONTH)
        val months = allMonths.sliceArray(0..currentMonthIndex)

        val barEntries = months.mapIndexed { index, month ->
            BarEntry(index.toFloat(), pendapatanMap[month] ?: 0f)
        }

        val barDataSet = BarDataSet(barEntries, "Pendapatan Bulanan (Rp)")
        barDataSet.color = "#E4C36C".toColorInt()
        barDataSet.setDrawValues(false)

        val barData = BarData(barDataSet)
        binding.barChartPendapatanBulanan.data = barData

        binding.barChartPendapatanBulanan.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(months.toList())
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }

        binding.barChartPendapatanBulanan.axisRight.isEnabled = false
        binding.barChartPendapatanBulanan.axisLeft.setDrawGridLines(false)

        binding.barChartPendapatanBulanan.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }

        binding.barChartPendapatanBulanan.description.isEnabled = false
        binding.barChartPendapatanBulanan.invalidate() // Refresh Chart
    }

    fun convertToMonthlyRevenueMap(pesananPerBulan: Map<String, PesananPerBulan>): Map<String, Float> {
        val monthNames = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        return pesananPerBulan.mapNotNull { (yearMonth, data) ->
            val parts = yearMonth.split("-") // Pisahkan "YYYY-MM"
            if (parts.size == 2) {
                val monthIndex =
                    parts[1].toIntOrNull()?.minus(1) // Bulan di Java/Kotlin mulai dari 0
                monthIndex?.let { monthNames[it] to data.totalOmset.toFloat() }
            } else {
                null // Jika format tidak sesuai, abaikan data
            }
        }.toMap()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}