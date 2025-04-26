package com.polije.sosrobahufactoryapp.ui.agen.home.peringkatStock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.polije.sosrobahufactoryapp.R

class StockAgenRankFragment : Fragment() {
    private var mViewModel: StockAgenRankViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock_agen_rank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel =
            ViewModelProvider(this).get<StockAgenRankViewModel?>(StockAgenRankViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance(): StockAgenRankFragment {
            return StockAgenRankFragment()
        }
    }
}