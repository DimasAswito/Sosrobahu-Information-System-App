package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.detailDaftarToko

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailDaftarTokoSalesBinding
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.BottomSheetEditTokoFragment
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.ItemRiwayatKunjunganAdapter
import com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component.tambahKunjungan.BottomSheetTambahKunjunganTokoFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailDaftarTokoSalesFragment : Fragment() {

    private var _binding: FragmentDetailDaftarTokoSalesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailDaftarTokoSalesViewModel by viewModel()

    private val args: DetailDaftarTokoSalesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    viewModel.deleteToko(args.detailToko.idDaftarToko ?: 0)
                    Toast.makeText(requireContext(), "Toko dihapus", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        childFragmentManager.setFragmentResultListener(
            "EDIT_TOKO",
            viewLifecycleOwner
        ) { requestKey, bundle ->
            val updatedNama = bundle.getString("namaToko") ?: return@setFragmentResultListener
            val updatedTelp = bundle.getString("noTelp") ?: return@setFragmentResultListener
            val updatedAlamat = bundle.getString("alamat") ?: return@setFragmentResultListener

            viewModel.updateToko(
                args.detailToko.idDaftarToko ?: 0,
                args.detailToko.namaToko.toString(),
                updatedNama,
                updatedTelp,
                updatedAlamat
            )
        }

        // 2. Contoh panggil BottomSheet saat tombol edit ditekan
        binding.tvEditToko.setOnClickListener {
            val toko = args.detailToko // method dummy ambil data toko terpilih
            BottomSheetEditTokoFragment
                .newInstance(
                    toko.namaToko.toString(),
                    toko.noTelp.toString(),
                    toko.lokasi.toString()
                )
                .show(childFragmentManager, "edit_toko")
        }



        binding.tvRiwayatKunjunganAll.setOnClickListener {
            val action =
                DetailDaftarTokoSalesFragmentDirections.actionDetailTokoSalesFragmentToListRiwayatKunjunganFragment(
                    args.detailToko.idDaftarToko ?: 0
                )
            findNavController().navigate(action)
        }

        val adapter = ItemRiwayatKunjunganAdapter(args.detailToko.kunjunganToko)
        binding.rvRiwayatKunjungan.layoutManager = LinearLayoutManager(context)
        binding.rvRiwayatKunjungan.adapter = adapter


        binding.tvAlamat.text = args.detailToko.lokasi
        binding.tvNoTelepon.text = args.detailToko.noTelp
        binding.tvTokoDetail.text = args.detailToko.namaToko

        binding.btnTambahKunjungan.setOnClickListener {
            val bottomSheet =
                BottomSheetTambahKunjunganTokoFragment.newInstance(
                    idToko = args.detailToko.idDaftarToko ?: 0
                )
            bottomSheet.show(parentFragmentManager, "BottomSheetTambahKunjungan")
        }
    }
}