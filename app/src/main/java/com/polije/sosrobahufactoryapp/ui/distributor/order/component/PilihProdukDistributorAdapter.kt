package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponseItem
import com.polije.sosrobahufactoryapp.databinding.ItemPilihProdukDistributorBinding

class PilihProdukDistributorAdapter :
    ListAdapter<PilihBarangPabrikDistributorResponseItem, PilihProdukDistributorAdapter.ViewHolder>(
        PilihProdukDistributorDiffUtill()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPilihProdukDistributorBinding.inflate(
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
        val getItem = getItem(position)
        holder.bind(getItem)
    }

    inner class ViewHolder(val binding: ItemPilihProdukDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PilihBarangPabrikDistributorResponseItem) {
            Glide.with(binding.root).load(BuildConfig.PICTURE_BASE_URL.plus(item.gambar))
                .into(binding.imgProduk)
        }
    }

    companion object {
        class PilihProdukDistributorDiffUtill :
            DiffUtil.ItemCallback<PilihBarangPabrikDistributorResponseItem>() {
            override fun areItemsTheSame(
                oldItem: PilihBarangPabrikDistributorResponseItem,
                newItem: PilihBarangPabrikDistributorResponseItem
            ): Boolean {
                return oldItem.idMasterBarang == newItem.idMasterBarang
            }

            override fun areContentsTheSame(
                oldItem: PilihBarangPabrikDistributorResponseItem,
                newItem: PilihBarangPabrikDistributorResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}