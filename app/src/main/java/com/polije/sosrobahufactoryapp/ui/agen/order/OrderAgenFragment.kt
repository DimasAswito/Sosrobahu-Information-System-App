package com.polije.sosrobahufactoryapp.ui.agen.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.ui.distributor.order.OrderDistributorFragment
import com.polije.sosrobahufactoryapp.ui.distributor.order.OrderDistributorViewModel
import kotlin.getValue

class OrderAgenFragment : Fragment() {
    companion object {
        fun newInstance() = OrderAgenFragment()
    }

    private val viewModel: OrderAgenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_order_agen, container, false)
    }
}