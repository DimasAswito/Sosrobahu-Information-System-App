package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.ProdukRestokItem
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestok
class PilihProdukAdapter(
    private val produkList: ProdukRestok,
    private val selectedList: MutableList<SelectedProdukRestok>,
    private val onItemSelected: (ProdukRestokItem) -> Unit
) : RecyclerView.Adapter<PilihProdukAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        val txtNamaProduk: TextView = itemView.findViewById(R.id.txtNamaProduk)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(produk: ProdukRestokItem) {
            // Load gambar jika dari resource. Kalau URL, ganti pakai Glide/Picasso
//            imgProduk.setImageResource(produk.gambar)

            txtNamaProduk.text = produk.namaRokok
            // Cek apakah produk ini sudah dipilih
            val isSelected = selectedList.any { it.item.idMasterBarang == produk.idMasterBarang }
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = isSelected

            // Listener checkbox
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onItemSelected(produk)
            }

            // Optional: klik seluruh item untuk toggle checkbox
            itemView.setOnClickListener {
                checkBox.isChecked = !checkBox.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pilih_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(produkList.barangPabriks[position])
    }

    override fun getItemCount(): Int = produkList.barangPabriks.size
}
