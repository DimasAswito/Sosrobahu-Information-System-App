package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.sales.ListSalesDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemDaftarTokoBinding

class ItemDaftarTokoAdapter(val action: DaftarTokoAdapterAction) :
    PagingDataAdapter<ListSalesDataItem, ItemDaftarTokoAdapter.ViewHolder>(
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
        fun bind(item: ListSalesDataItem?) {
            if (item != null) {
                binding.namaDistributor.text = item.namaPemilik
                binding.alamatDistributor.text = item.lokasi
                binding.root.setOnClickListener {
                    action.onDaftarTokoClicked(item)
                }
            }
        }
    }

    companion object {
        class ItemDaftarTokoDiffUtill : DiffUtil.ItemCallback<ListSalesDataItem>() {
            override fun areItemsTheSame(
                oldItem: ListSalesDataItem,
                newItem: ListSalesDataItem
            ): Boolean {
                return oldItem.idDaftarToko == newItem.idDaftarToko
            }

            override fun areContentsTheSame(
                oldItem: ListSalesDataItem,
                newItem: ListSalesDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface DaftarTokoAdapterAction {
        fun onDaftarTokoClicked(item: ListSalesDataItem)
    }
}