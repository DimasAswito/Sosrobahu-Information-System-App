package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailDaftarTokoSalesBinding
import com.polije.sosrobahufactoryapp.ui.agen.dashboard.DashboardAgenFragmentDirections
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.BottomSheetEditTokoFragment
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.BottomSheetTambahKunjunganTokoFragment
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.ItemRiwayatKunjunganAdapter
import com.polije.sosrobahufactoryapp.ui.sales.dashboard.DashboardSalesFragmentDirections

class DetailDaftarTokoSalesFragment : Fragment() {

    private var _binding: FragmentDetailDaftarTokoSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailDaftarTokoSalesViewModel by viewModels()

    private val args: DetailDaftarTokoSalesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDaftarTokoSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnDeleteToko.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin menghapus toko?")
                .setPositiveButton("Ya") { dialog, _ ->
                    Toast.makeText(requireContext(), "Toko dihapus", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.tvEditToko.setOnClickListener {
            val namaToko = binding.tvTokoDetail.text.toString()
            val noTelp = binding.tvNoTelepon.text.toString()
            val alamat = binding.tvAlamat.text.toString()

            val bottomSheet = BottomSheetEditTokoFragment.newInstance(namaToko, noTelp, alamat)
            bottomSheet.show(parentFragmentManager, "BottomSheetEditToko")
        }


        binding.tvRiwayatKunjunganAll.setOnClickListener {
            val action = DetailDaftarTokoSalesFragmentDirections.actionDetailTokoSalesFragmentToListRiwayatKunjunganFragment()
            findNavController().navigate(action)
        }

        val adapter = ItemRiwayatKunjunganAdapter(args.detailToko.kunjunganToko)
        binding.rvRiwayatKunjungan.layoutManager = LinearLayoutManager(context)
        binding.rvRiwayatKunjungan.adapter = adapter


        binding.tvAlamat.text = args.detailToko.lokasi
        binding.tvNoTelepon.text = args.detailToko.noTelp
        binding.tvTokoDetail.text = args.detailToko.namaToko

        binding.BtnTambahKunjungan.setOnClickListener {
            val bottomSheet = BottomSheetTambahKunjunganTokoFragment()
            bottomSheet.show(parentFragmentManager, "BottomSheetTambahKunjungan")
        }
    }
}