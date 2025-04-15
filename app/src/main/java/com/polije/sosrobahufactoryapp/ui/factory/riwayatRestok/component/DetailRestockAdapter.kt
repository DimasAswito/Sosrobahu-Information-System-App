package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.DetailProdukItem
import com.polije.sosrobahufactoryapp.databinding.DetailRestockItemBinding
import com.polije.sosrobahufactoryapp.databinding.FragmentDetailRestokBinding

class DetailRestockAdapter(private val listDetailRestoctItem : List<DetailProdukItem?>) : RecyclerView.Adapter<DetailRestockAdapter.ProdukViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProdukViewHolder {
        val binding = DetailRestockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProdukViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProdukViewHolder,
        position: Int
    ) {
        val produk = listDetailRestoctItem[position]
        holder.binding.tvNamaProduk.text = produk?.namaRokok
        holder.binding.tvJumlah.text = produk?.jumlah.toString()
    }

    override fun getItemCount(): Int = listDetailRestoctItem.count()

    inner class ProdukViewHolder(val binding : DetailRestockItemBinding) : RecyclerView.ViewHolder(binding.root)
}