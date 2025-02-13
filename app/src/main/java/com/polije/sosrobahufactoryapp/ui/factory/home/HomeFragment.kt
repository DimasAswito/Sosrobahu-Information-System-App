package com.polije.sosrobahufactoryapp.ui.factory.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.polije.sosrobahufactoryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupBarChart()
        setupLineChart()

        return root
    }

    private fun setupBarChart() {
        val barEntries = listOf(
            BarEntry(1f, 10f),
            BarEntry(2f, 20f),
            BarEntry(3f, 15f),
            BarEntry(4f, 25f),
            BarEntry(5f, 30f)
        )

        val barDataSet = BarDataSet(barEntries, "Data Produksi")
        barDataSet.color = Color.BLUE

        val barData = BarData(barDataSet)
        binding.barChart.data = barData
        binding.barChart.description.isEnabled = false
        binding.barChart.invalidate()
    }

    private fun setupLineChart() {
        val lineEntries = listOf(
            Entry(1f, 5f),
            Entry(2f, 10f),
            Entry(3f, 7f),
            Entry(4f, 15f),
            Entry(5f, 12f)
        )

        val lineDataSet = LineDataSet(lineEntries, "Performa Mesin")
        lineDataSet.color = Color.RED
        lineDataSet.setCircleColor(Color.RED)

        val lineData = LineData(lineDataSet)
        binding.lineChart.data = lineData
        binding.lineChart.description.isEnabled = false
        binding.lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
