package com.polije.sosrobahufactoryapp.ui.sales.home.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemHomeSalesBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class ItemHomeSalesAdapter :
    ListAdapter<ListBarangAgenSalesDataItem, ItemHomeSalesAdapter.ViewHolder>(ItemHomeSalesDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemHomeSalesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemHomeSalesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListBarangAgenSalesDataItem) {
            Glide.with(binding.root)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.gambar)
                .into(binding.imagehome)
            binding.tvNamaProduk.text = item.namaRokok
            binding.tvHarga.text = item.harga.toRupiah()
        }
    }

    companion object {
        class ItemHomeSalesDiffUtil : DiffUtil.ItemCallback<ListBarangAgenSalesDataItem>() {
            override fun areItemsTheSame(
                oldItem: ListBarangAgenSalesDataItem,
                newItem: ListBarangAgenSalesDataItem
            ): Boolean {
                return oldItem.idBarangAgen == newItem.idBarangAgen
            }

            override fun areContentsTheSame(
                oldItem: ListBarangAgenSalesDataItem,
                newItem: ListBarangAgenSalesDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}