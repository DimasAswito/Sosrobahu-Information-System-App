package com.polije.sosrobahufactoryapp.ui.factory.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginActivity
import com.polije.sosrobahufactoryapp.ui.factory.login.FactoryLoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import androidx.core.graphics.toColorInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

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
            findNavController().navigate(R.id.action_navigation_home_to_topProductFragment)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {

        homeViewModel.state.collectLatest { state ->
            if (state.dashboardPabrik != null){
                binding.stokPabrikTersedia.text = "${state.dashboardPabrik.finalStockKarton} karton"
                binding.omsetPabrik.text = state.dashboardPabrik.totalPendapatan
                binding.jumlahDistributor.text = "${state.dashboardPabrik.totalDistributor} Distributor"


                binding.topProductName.text = state.dashboardPabrik.topProductName
//                binding.topProductStock.text = "${state.dashboardPabrik.}"
//                val imageUrl = "$BASE_URL_PRODUK${it.image}"
//                Glide.with(requireContext()).load(imageUrl).into(binding.topProductImage)

                setupBarChartPendapatan(state.pendapatanBulanan.mapValues { it.value.totalOmset.toFloat() })
            }

            if (state.errorMessage != null){
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG).show()
            }
        }

//        homeViewModel.topProduct.observe(viewLifecycleOwner) { topProduct ->
//            topProduct?.let {
//                binding.topProductName.text = it.name
//                binding.topProductStock.text = "${it.stock}"
//                val imageUrl = "$BASE_URL_PRODUK${it.image}"
//                Glide.with(requireContext()).load(imageUrl).into(binding.topProductImage)
//            }
//        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



