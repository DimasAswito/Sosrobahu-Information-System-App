package com.polije.sosrobahufactoryapp.ui.factory.pesanan.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.pabrik.ItemNotaItem
import com.polije.sosrobahufactoryapp.databinding.DetailPesananItemBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class DetailPesananItemPabrikAdapter :
    ListAdapter<ItemNotaItem, DetailPesananItemPabrikAdapter.ItemNotaViewHolder>(DIFF_CALLBACK) {

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
            tvHargaSatuan.text = item.hargaSatuan.toRupiah()
            tvJumlahItem.text = item.jumlahItem.toString()
            tvJumlahHarga.text = item.jumlahHarga.toRupiah()
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