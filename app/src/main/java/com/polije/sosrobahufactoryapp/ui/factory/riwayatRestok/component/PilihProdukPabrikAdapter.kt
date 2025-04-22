package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig.PICTURE_BASE_URL
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.data.model.ProdukRestokItem
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestok
class PilihProdukPabrikAdapter(
    private val produkList: ProdukRestok,
    private val selectedList: MutableList<SelectedProdukRestok>,
    private val onItemSelected: (ProdukRestokItem, Boolean) -> Unit
) : RecyclerView.Adapter<PilihProdukPabrikAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        private val txtNamaProduk: TextView = itemView.findViewById(R.id.txtNamaProduk)
        private val cardProduk: CardView = itemView.findViewById(R.id.cardProduk)

    val progressDrawable = CircularProgressDrawable(itemView.context).apply {
        strokeWidth = 6f
        centerRadius = 30f
        start()
    }
        fun bind(produk: ProdukRestokItem) {

            Glide.with(itemView.context)
                .load(PICTURE_BASE_URL+ "produk/" + produk.gambar)
                .placeholder(progressDrawable) // pakai animasi loading
                .error(R.drawable.logo) // fallback kalau gagal
                .into(imgProduk)


            txtNamaProduk.text = produk.namaRokok

            // Cek apakah produk termasuk yang dipilih
            val isSelected = selectedList.any { it.item.idMasterBarang == produk.idMasterBarang }

            // Ubah warna background jika dipilih
            cardProduk.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (isSelected) R.color.abu_abu_cerah else android.R.color.white
                )
            )

            // Handle klik
            itemView.setOnClickListener {
                val baruDipilih = !isSelected
                if (baruDipilih) {
                    selectedList.add(SelectedProdukRestok(produk))
                } else {
                    selectedList.removeAll { it.item.idMasterBarang == produk.idMasterBarang }
                }

                notifyItemChanged(adapterPosition)
                onItemSelected(produk, baruDipilih)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pilih_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(produkList.barangPabriks[position])
    }

    override fun getItemCount(): Int = produkList.barangPabriks.size
}
