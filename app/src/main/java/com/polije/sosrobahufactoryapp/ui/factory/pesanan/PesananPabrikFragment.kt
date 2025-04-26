package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukItem
import com.polije.sosrobahufactoryapp.databinding.FragmentPesananBinding
import com.polije.sosrobahufactoryapp.ui.factory.dashboard.DashboardPabrikFragmentDirections
import com.polije.sosrobahufactoryapp.ui.factory.pesanan.component.PesananPabrikAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PesananPabrikFragment : Fragment() {

    private var _binding: FragmentPesananBinding? = null
    private val binding get() = _binding!!

    private val pesananViewModel: PesananPabrikViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesananBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pesananAdapter = PesananPabrikAdapter(object : PesananPabrikAdapter.OnItemClickListener {
            override fun onItemClick(pesanan: PesananMasukItem) {
                val action =
                    DashboardPabrikFragmentDirections.actionDashboardFragmentToDetailPesananFragment(pesanan)
                requireActivity().findNavController(R.id.fragmentContainerView).navigate(action)
            }
        })
        binding.recyclerViewPesanan.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPesanan.adapter = pesananAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            pesananViewModel.getPesananMasuk().collectLatest { pesananMasuk ->
                pesananAdapter.submitData(pesananMasuk)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener{
            pesananViewModel.getPesananMasuk()
            binding.swipeRefreshLayout.isRefreshing = false
        }



//        btnFilter.setOnClickListener {
//            showFilterDialog()
//        }
    }


//    private fun showFilterDialog() {
//        val options =
//            arrayOf("Urut berdasarkan Tanggal", "Urut berdasarkan Nama", "Tampilkan Semua")
//
//        AlertDialog.Builder(requireContext()).setTitle("Filter Pesanan")
//            .setItems(options) { _, which ->
//                when (which) {
//                    0 -> sortPesananByTanggal()
//                    1 -> sortPesananByNama()
//                    2 -> resetPesanan()
//                }
//            }.show()
//    }

}
