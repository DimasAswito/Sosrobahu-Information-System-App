package com.polije.sosrobahufactoryapp.ui.factory.hargaProduk.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.Produk

class ProdukPabrikAdapter(private var produkList: List<Produk>) :
    RecyclerView.Adapter<ProdukPabrikAdapter.ViewHolder>() {

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

        // Tambahkan onClickListener untuk navigasi ke DetailHargaFragment
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("namaProduk", produk.nama)
                putString("hargaProduk", produk.harga)
                putInt("gambarProduk", produk.gambar)
            }

            // Pindah ke DetailHargaFragment tanpa nav_graph.xml
            it.findNavController().navigate(R.id.action_navigation_harga_to_detailHargaFragment, bundle)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            produkList
        } else {
            produkList.filter { it.nama.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}


