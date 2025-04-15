package com.polije.sosrobahufactoryapp.ui.factory.pesanan.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.ItemNotaItem
import com.polije.sosrobahufactoryapp.databinding.DetailPesananItemBinding

class DetailPesananItemAdapter :
    ListAdapter<ItemNotaItem, DetailPesananItemAdapter.ItemNotaViewHolder>(DIFF_CALLBACK) {

    inner class ItemNotaViewHolder(val binding: DetailPesananItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNotaViewHolder {
        val binding = DetailPesananItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemNotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemNotaViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvNamaRokok.text = item.namaRokok
            tvHargaSatuan.text = "Harga Satuan: Rp${item.hargaSatuan}"
            tvJumlahItem.text = "Jumlah Item: ${item.jumlahItem}"
            tvJumlahHarga.text = "Jumlah Harga: Rp${item.jumlahHarga}"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemNotaItem>() {
            override fun areItemsTheSame(oldItem: ItemNotaItem, newItem: ItemNotaItem): Boolean {
                return oldItem.namaRokok == newItem.namaRokok
            }

            override fun areContentsTheSame(oldItem: ItemNotaItem, newItem: ItemNotaItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}