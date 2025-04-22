package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.detailRestok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailRestokBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component.DetailRestockPabrikAdapter
import kotlin.getValue

class DetailRestokPabrikFragment : Fragment() {


    private var _binding: FragmentDetailRestokBinding? = null
    private val binding get() = _binding!!

    private val args: DetailRestokPabrikFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailRestokBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Ambil data yang dikirim dari RiwayatFragment

        binding.txtJumlah.text = args.restockDetail.jumlah

        binding.txtTitle.text = getString(R.string.detail_restock,args.restockDetail.tanggal.toString(),args.restockDetail.idRestock)

        val adapter = DetailRestockPabrikAdapter(args.restockDetail.detailProduk)
        binding.rvListProduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListProduk.adapter = adapter

        // Aksi tombol cetak (bisa dihubungkan dengan fungsi cetak PDF)
        binding.btnCetak.setOnClickListener {
            Toast.makeText(requireContext(), "Mencetak riwayat...", Toast.LENGTH_SHORT).show()
        }
    }
}
