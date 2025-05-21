package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.listRiwayatKunjungan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentListRiwayatKunjunganBinding
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.ItemRiwayatKunjunganAllAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListRiwayatKunjunganFragment : Fragment() {

    private var _binding: FragmentListRiwayatKunjunganBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListRiwayatKunjunganViewModel by viewModel()

    val args: ListRiwayatKunjunganFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRiwayatKunjunganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = ItemRiwayatKunjunganAllAdapter()
        binding.recyclerViewListRiwayatKunjungan.adapter = adapter
        binding.recyclerViewListRiwayatKunjungan.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewModel.getListKunjungan(idToko = args.idToko).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}