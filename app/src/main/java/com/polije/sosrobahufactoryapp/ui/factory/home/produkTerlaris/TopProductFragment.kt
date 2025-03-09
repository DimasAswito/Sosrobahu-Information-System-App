package com.polije.sosrobahufactoryapp.ui.factory.home.produkTerlaris

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentTopProductBinding
import com.polije.sosrobahufactoryapp.model.TopSellingProduct
import com.polije.sosrobahufactoryapp.ui.factory.home.component.TopSellingProductAdapter

class TopProductFragment : Fragment() {

    private lateinit var binding: FragmentTopProductBinding
    private lateinit var adapter: TopSellingProductAdapter
    private val productList = mutableListOf<TopSellingProduct>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopSellingProductAdapter(productList)
        binding.recyclerViewTopProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTopProduct.adapter = adapter

        loadTopSellingProducts()

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun loadTopSellingProducts() {
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
            }
        }

        // Urutkan berdasarkan pendapatan tertinggi
        val sortedProducts = productRevenueMap.entries.sortedByDescending { it.value }

        productList.clear()

        for ((index, entry) in sortedProducts.take(10).withIndex()) {
            val product = productData.firstOrNull {
                it.split(",")[0].toIntOrNull() == entry.key
            }
            if (product != null) {
                val splitProduct = product.split(",")
                val productName = splitProduct[1]
                val productImage = splitProduct[3]
                val totalRevenue = entry.value

                productList.add(TopSellingProduct(index + 1, productName, productImage, totalRevenue))
            }
        }

        adapter.notifyDataSetChanged()
    }
}
