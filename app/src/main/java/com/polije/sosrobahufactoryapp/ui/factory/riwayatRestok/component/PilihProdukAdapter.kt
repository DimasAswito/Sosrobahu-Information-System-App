package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.Produk

class PilihProdukAdapter(
    private val produkList: List<Produk>, private val onItemSelected: (Produk, Boolean) -> Unit
) : RecyclerView.Adapter<PilihProdukAdapter.ViewHolder>() {

    private val selectedItems = mutableSetOf<Produk>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        val txtNamaProduk: TextView = itemView.findViewById(R.id.txtNamaProduk)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(produk: Produk) {
            imgProduk.setImageResource(produk.gambar)
            txtNamaProduk.text = produk.nama
            checkBox.isChecked = selectedItems.contains(produk)

            checkBox.setOnCheckedChangeListener(null) // Hindari pemicu ulang listener
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) selectedItems.add(produk) else selectedItems.remove(produk)
                onItemSelected(produk, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pilih_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(produkList[position])
    }

    override fun getItemCount(): Int = produkList.size
}
