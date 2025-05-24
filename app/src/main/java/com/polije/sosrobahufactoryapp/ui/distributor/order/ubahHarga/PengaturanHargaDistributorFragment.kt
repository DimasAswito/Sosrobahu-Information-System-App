package com.polije.sosrobahufactoryapp.ui.distributor.order.ubahHarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.data.model.distributor.DistributorBarangItems
import com.polije.sosrobahufactoryapp.databinding.FragmentPengaturanHargaDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.component.PengaturanHargaAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PengaturanHargaDistributorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PengaturanHargaDistributorFragment : Fragment() {

    private var _binding: FragmentPengaturanHargaDistributorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PengaturanHargaDistributorViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPengaturanHargaDistributorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PengaturanHargaAdapter(object : PengaturanHargaAdapter.PengaturanHargaAction {
            override fun onPengaturanHargaItemClicked(item: DistributorBarangItems) {

            }
        })

        binding.rvHarga.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                if (state.data.isNotEmpty()) {
                    adapter.submitList(state.data)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PengaturanHargaDistributorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PengaturanHargaDistributorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}