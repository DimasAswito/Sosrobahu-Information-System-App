package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.RiwayatRestok

class RiwayatRestokAdapter(private val riwayatList: List<RiwayatRestok>) :
    RecyclerView.Adapter<RiwayatRestokAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        val txtNamaProduk: TextView = itemView.findViewById(R.id.txtNamaProduk)
        val txtTanggalRestok: TextView = itemView.findViewById(R.id.txtTanggalRestok)
        val txtJumlahProduk: TextView = itemView.findViewById(R.id.txtJumlahProduk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_restok, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = riwayatList[position]
        holder.imgProduk.setImageResource(riwayat.gambar)
        holder.txtNamaProduk.text = riwayat.namaProduk
        holder.txtTanggalRestok.text = riwayat.tanggalRestok
        holder.txtJumlahProduk.text = "Jumlah Produk: ${riwayat.jumlahProduk}"
    }

    override fun getItemCount(): Int = riwayatList.size
}
