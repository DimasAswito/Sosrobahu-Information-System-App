package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.R

class DetailPesananFragment : Fragment() {

    private lateinit var tvDistributor: TextView
    private lateinit var tvTanggal: TextView
    private lateinit var tvJumlah: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvTotalHarga: TextView
    private lateinit var btnCetak: Button
    private lateinit var btnBuktiPembayaran: Button
    private lateinit var imgBuktiPembayaran: ImageView
    private var isImageVisible = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_pesanan, container, false)

        tvDistributor = view.findViewById(R.id.tvDistributorDetail)
        tvTanggal = view.findViewById(R.id.tvTanggalDetail)
        tvJumlah = view.findViewById(R.id.tvJumlahDetail)
        tvStatus = view.findViewById(R.id.tvStatusPembayaran)
        tvTotalHarga = view.findViewById(R.id.tvTotalHargaDetail)
        btnCetak = view.findViewById(R.id.CetakLaporanButton)

        // Ambil data dari arguments
        val distributor = arguments?.getString("distributor") ?: "Unknown"
        val tanggal = arguments?.getString("tanggal") ?: "Unknown"
        val totalHarga = arguments?.getInt("totalHarga") ?: 0
        val status = arguments?.getString("status") ?: "Unknown"

        tvDistributor.text = distributor
        tvTanggal.text = tanggal
        tvJumlah.text = "Jumlah: ${totalHarga / 50000}" // Misalnya, setiap unit Rp50.000
        tvStatus.text = "Status Pembayaran: $status"
        tvTotalHarga.text = "Total: Rp $totalHarga"

        btnBuktiPembayaran = view.findViewById(R.id.btnBuktiPembayaran)
        imgBuktiPembayaran = view.findViewById(R.id.imgBuktiPembayaran)

        // Ambil URL atau gambar lokal, misal dari bundle
        val urlGambar = "https://example.com/bukti_pembayaran.jpg"

        // Set gambar menggunakan Glide (pastikan sudah tambahkan dependensi)
        Glide.with(requireContext())
            .load(urlGambar)
            .placeholder(R.drawable.logo) // Gambar sementara
            .error(R.drawable.logo) // Gambar jika gagal
            .into(imgBuktiPembayaran)

        btnBuktiPembayaran.setOnClickListener {
            isImageVisible = !isImageVisible
            imgBuktiPembayaran.visibility = if (isImageVisible) View.VISIBLE else View.GONE
        }

        btnCetak.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Cetak Belum Tersedia", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
