package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.Produk

class ProdukAdapter(private var produkList: List<Produk>) :
    RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    private var filteredList: List<Produk> = produkList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        val txtNama: TextView = itemView.findViewById(R.id.txtNamaProduk)
        val txtHarga: TextView = itemView.findViewById(R.id.txtHargaProduk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = filteredList[position]
        holder.imgProduk.setImageResource(produk.gambar)
        holder.txtNama.text = produk.nama
        holder.txtHarga.text = produk.harga
    }

    override fun getItemCount(): Int = filteredList.size

    // Tambahkan fungsi filter
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            produkList
        } else {
            produkList.filter { it.nama.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}

