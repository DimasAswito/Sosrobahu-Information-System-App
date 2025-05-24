package com.polije.sosrobahufactoryapp.ui.agen.home.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.data.model.agen.StokBarangAgenItem
import com.polije.sosrobahufactoryapp.databinding.ItemHomeAgenBinding

class ItemHomeAgenAdapter :
    ListAdapter<StokBarangAgenItem, ItemHomeAgenAdapter.ViewHolder>(ItemHomeAgenDiffUtill()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemHomeAgenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemHomeAgenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StokBarangAgenItem) {
            Glide.with(binding.root)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.gambar)
                .into(binding.imagehome)
            binding.namaProduk.text = item.namaRokok
            binding.nominalSisaStok.text = item.stok.toString() + " Slop"
        }
    }

    companion object {
        class ItemHomeAgenDiffUtill : DiffUtil.ItemCallback<StokBarangAgenItem>() {
            override fun areItemsTheSame(
                oldItem: StokBarangAgenItem,
                newItem: StokBarangAgenItem
            ): Boolean {
                return oldItem.namaRokok == newItem.namaRokok
            }

            override fun areContentsTheSame(
                oldItem: StokBarangAgenItem,
                newItem: StokBarangAgenItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}