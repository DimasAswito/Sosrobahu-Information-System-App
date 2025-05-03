package com.polije.sosrobahufactoryapp.ui.distributor.pesanan.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.distributor.ItemNotaDistributorItem
import com.polije.sosrobahufactoryapp.databinding.ItemDetailPesananDistributorBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class ItemDetailPesananDistributorAdapter :
    ListAdapter<ItemNotaDistributorItem, ItemDetailPesananDistributorAdapter.ViewHolder>(
        ItemDetailPesananDistributorDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDetailPesananDistributorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemDetailPesananDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemNotaDistributorItem) {
            with(binding) {
                tvNamaRokok.text = item.namaRokok
                tvJumlahItem.text = item.jumlahItem.toString()
                tvHargaSatuan.text = item.hargaSatuan?.toRupiah()
                tvJumlahHarga.text = item.jumlahHarga?.toRupiah()
            }
        }
    }

    companion object {
        class ItemDetailPesananDistributorDiffUtil :
            DiffUtil.ItemCallback<ItemNotaDistributorItem>() {
            override fun areItemsTheSame(
                oldItem: ItemNotaDistributorItem,
                newItem: ItemNotaDistributorItem
            ): Boolean {
                return oldItem.namaRokok == newItem.namaRokok
            }

            override fun areContentsTheSame(
                oldItem: ItemNotaDistributorItem,
                newItem: ItemNotaDistributorItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}