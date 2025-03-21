package com.polije.sosrobahufactoryapp.ui.factory.pesanan.component

import Pesanan
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R

class PesananAdapter(
    private val listPesanan: List<Pesanan>,
    private val navController: NavController
) :
    RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    class PesananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistributor: TextView = itemView.findViewById(R.id.tvDistributor)

        //        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvTotalHarga: TextView = itemView.findViewById(R.id.tvTotalHarga)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan, parent, false)
        return PesananViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        val pesanan = listPesanan[position]
//        holder.tvDistributor.text = pesanan.distributor
////        holder.tvTanggal.text = pesanan.tanggal
//        holder.tvTotalHarga.text = "Rp ${pesanan.totalHarga}"
//
//        // Atur status dengan warna
//        holder.tvStatus.text = pesanan.status
//        holder.tvStatus.isSelected = pesanan.status == "Selesai"
//        holder.tvStatus.isActivated = pesanan.status == "Ditolak"
//        holder.tvStatus.setBackgroundResource(R.drawable.status_background)
//
//
//        // Klik item untuk navigasi ke DetailPesananFragment dengan Bundle
//        holder.itemView.setOnClickListener {
//            val bundle = Bundle().apply {
//                putString("distributor", pesanan.distributor)
//                putString("tanggal", pesanan.tanggal)
//                putInt("totalHarga", pesanan.totalHarga)
//                putString("status", pesanan.status)
//            }
//            navController.navigate(R.id.action_navigation_pesanan_to_detailPesananFragment, bundle)
//        }
    }

    override fun getItemCount() = listPesanan.size
}


