package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.ItemTambahOrderDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor

class TambahOrderDistributorAdapter(val listItem: List<SelectedProdukDistributor>) :
    RecyclerView.Adapter<TambahOrderDistributorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTambahOrderDistributorBinding.inflate(
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
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.count()

    inner class ViewHolder(val binding: ItemTambahOrderDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SelectedProdukDistributor) {
            binding.txtNamaProduk.text = item.item.namaRokok
            Glide.with(binding.root.context)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.item.gambar)
                .error(R.drawable.logo)
                .into(binding.imgProduk)
        }
    }
}