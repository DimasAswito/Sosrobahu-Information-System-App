package com.polije.sosrobahufactoryapp.ui.distributor.home.peringkatStok

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.R

class StockDistributorRankFragment : Fragment() {

    companion object {
        fun newInstance() = StockDistributorRankFragment()
    }

    private val viewModel: StockDistributorRankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_stock_distributor_rank, container, false)
    }
}