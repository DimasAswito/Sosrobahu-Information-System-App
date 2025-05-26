package com.polije.sosrobahufactoryapp.ui.factory.home.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.pabrik.LaporanBulanan

class LaporanBulananPabrikAdapter(private val laporanList: List<LaporanBulanan>) :
    RecyclerView.Adapter<LaporanBulananPabrikAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistributorName: TextView = itemView.findViewById(R.id.tvDistributorName)
        val tvTanggal: TextView = itemView.findViewById(R.id.tanggal)
        val tvJumlah: TextView = itemView.findViewById(R.id.jumlah)
        val tvTotalHarga: TextView = itemView.findViewById(R.id.tvTotalHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laporan_bulanan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val laporan = laporanList[position]
        holder.tvDistributorName.text = laporan.distributor
        holder.tvTanggal.text = laporan.tanggal
        holder.tvJumlah.text = laporan.jumlah.toString()
        holder.tvTotalHarga.text = laporan.totalHarga

        // Tambahkan klik item untuk pindah ke DetailLaporanFragment
        holder.itemView.setOnClickListener {
            // Siapkan listBarangAgen dalam Bundle
            val bundle = Bundle().apply {
                putString("distributor", laporan.distributor)
                putString("tanggal", laporan.tanggal)
                putInt("jumlah", laporan.jumlah)
                putString("totalHarga", laporan.totalHarga)
            }
            // Arahkan ke DetailLaporanFragment (pastikan action di nav_graph sudah ada)
//            it.findNavController().navigate(
//                R.id.action_laporanBulananFragment_to_detailLaporanFragment,
//                bundle
//            )
        }
    }

    override fun getItemCount(): Int = laporanList.size
}

