package com.polije.sosrobahufactoryapp.ui.distributor.home.component

import ProdukDataItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.databinding.ItemHomeDistributorBinding


class ItemHomeDistributorAdapter :
    ListAdapter<ProdukDataItem, ItemHomeDistributorAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemHomeDistributorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: ItemHomeDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProdukDataItem) {
            binding.namaProduk.text = item.namaRokok
            binding.nominalSisaStok.text = item.totalProduk.toString()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProdukDataItem>() {
            override fun areItemsTheSame(old: ProdukDataItem, new: ProdukDataItem) =
                old.namaRokok == new.namaRokok

            override fun areContentsTheSame(old: ProdukDataItem, new: ProdukDataItem) =
                old == new
        }
    }


}