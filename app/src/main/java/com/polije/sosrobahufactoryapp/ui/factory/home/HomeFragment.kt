package com.polije.sosrobahufactoryapp.ui.factory.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Data dummy nama-nama bulan
    private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupBarChart()
        setupLineChart()

        binding.barChart.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_laporanBulananFragment)
        }

        // 2. Tangani klik LineChart
        binding.lineChart.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_laporanBulananFragment)
        }


        return root
    }

    private fun setupBarChart() {
        // Pendapatan bulanan (Rp) dengan nilai random
        val barEntries = months.indices.map { index ->
            // Contoh random antara 1 juta - 10 juta
            val randomPendapatan = (1_000_000..10_000_000).random().toFloat()
            BarEntry(index.toFloat(), randomPendapatan)
        }

        val barDataSet = BarDataSet(barEntries, "Pendapatan Bulanan (Rp)")
        barDataSet.color = Color.BLUE
        // Hilangkan nilai di atas batang
        barDataSet.setDrawValues(false)

        val barData = BarData(barDataSet)
        binding.barChart.data = barData

        // Format sumbu X dengan nama bulan
        binding.barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(months)
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
        }

        // Hilangkan sumbu kanan (tulisan di kanan)
        binding.barChart.axisRight.isEnabled = false

        // Pindahkan legend ke tengah bawah
        binding.barChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }

        // Hilangkan description default
        binding.barChart.description.isEnabled = false

        binding.barChart.invalidate() // Refresh
    }

    private fun setupLineChart() {
        // Penjualan bulanan (karton) dengan nilai random
        val lineEntries = months.indices.map { index ->
            // Contoh random antara 1 - 100 karton
            val randomPenjualan = (1..100).random().toFloat()
            Entry(index.toFloat(), randomPenjualan)
        }

        val lineDataSet = LineDataSet(lineEntries, "Penjualan Bulanan (Karton)")
        lineDataSet.color = Color.RED
        lineDataSet.setCircleColor(Color.RED)
        // Hilangkan nilai di atas titik
        lineDataSet.setDrawValues(false)

        val lineData = LineData(lineDataSet)
        binding.lineChart.data = lineData

        // Format sumbu X dengan nama bulan
        binding.lineChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(months)
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
        }

        // Hilangkan sumbu kanan (tulisan di kanan)
        binding.lineChart.axisRight.isEnabled = false

        // Pindahkan legend ke tengah bawah
        binding.lineChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }

        // Hilangkan description default
        binding.lineChart.description.isEnabled = false

        binding.lineChart.invalidate() // Refresh
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

