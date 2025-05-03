package com.polije.sosrobahufactoryapp.ui.distributor.order.tambahOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentTambahOrderDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.TambahOrderDistributorAdapter

class TambahOrderDistributorFragment : Fragment() {

    private var _binding: FragmentTambahOrderDistributorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TambahOrderDistributorViewModel by viewModels()

    private val args: TambahOrderDistributorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahOrderDistributorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val adapter = TambahOrderDistributorAdapter(args.listItemTerpilih.data)
        binding.recyclerViewTambahOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTambahOrder.adapter = adapter


        binding.textTransferInfo.text = getString(
            R.string.transfer_info_text,
            args.listItemTerpilih.namaBank,
            args.listItemTerpilih.norek.toString(),
            args.listItemTerpilih.namaLengkap
        )

    }
}