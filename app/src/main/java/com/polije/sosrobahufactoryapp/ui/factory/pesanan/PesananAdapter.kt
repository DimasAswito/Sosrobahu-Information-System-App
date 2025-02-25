package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.Pesanan

class PesananAdapter(private val listPesanan: List<Pesanan>, private val navController: NavController) :
    RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    class PesananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistributor: TextView = itemView.findViewById(R.id.tvDistributor)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
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
        holder.tvDistributor.text = pesanan.distributor
        holder.tvTanggal.text = pesanan.tanggal
        holder.tvTotalHarga.text = "Rp ${pesanan.totalHarga}"

        // Atur status dengan warna
        if (pesanan.status == "Selesai") {
            holder.tvStatus.text = "Selesai"
            holder.tvStatus.setBackgroundResource(R.color.green)
        } else {
            holder.tvStatus.text = "Diproses"
            holder.tvStatus.setBackgroundResource(R.color.yellow)
        }

        // Klik item untuk navigasi ke DetailPesananFragment dengan Bundle
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("distributor", pesanan.distributor)
                putString("tanggal", pesanan.tanggal)
                putInt("totalHarga", pesanan.totalHarga)
                putString("status", pesanan.status)
            }
            navController.navigate(R.id.action_navigation_pesanan_to_detailPesananFragment, bundle)
        }
    }

    override fun getItemCount() = listPesanan.size
}


