package com.polije.sosrobahufactoryapp.ui.sales.daftarToko

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDaftarTokoSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.ItemDaftarTokoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DaftarTokoSalesFragment : Fragment() {

    private var _binding : FragmentDaftarTokoSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DaftarTokoSalesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding     = FragmentDaftarTokoSalesBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ItemDaftarTokoAdapter()
        binding.recyclerViewDaftarToko.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewDaftarToko.adapter = adapter

        lifecycleScope.launch {
            viewModel.listToko().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}