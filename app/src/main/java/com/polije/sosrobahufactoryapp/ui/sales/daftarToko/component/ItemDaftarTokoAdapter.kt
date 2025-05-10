package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.sales.ListTokoSalesDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemDaftarTokoBinding

class ItemDaftarTokoAdapter :
    PagingDataAdapter<ListTokoSalesDataItem, ItemDaftarTokoAdapter.ViewHolder>(
        ItemDaftarTokoDiffUtill()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemDaftarTokoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemDaftarTokoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListTokoSalesDataItem?) {
            if (item != null) {
                binding.namaDistributor.text = item.namaPemilik
                binding.alamatDistributor.text = item.lokasi
            }
        }
    }

    companion object {
        class ItemDaftarTokoDiffUtill : DiffUtil.ItemCallback<ListTokoSalesDataItem>() {
            override fun areItemsTheSame(
                oldItem: ListTokoSalesDataItem,
                newItem: ListTokoSalesDataItem
            ): Boolean {
                return oldItem.idDaftarToko == newItem.idDaftarToko
            }

            override fun areContentsTheSame(
                oldItem: ListTokoSalesDataItem,
                newItem: ListTokoSalesDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}