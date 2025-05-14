package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.sales.KunjunganTokoDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemRiwayatKunjunganAllBinding
import com.polije.sosrobahufactoryapp.utils.toTanggalIndonesia
import com.polije.sosrobahufactoryapp.utils.toTanggalIndonesiaInstant

class ItemRiwayatKunjunganAllAdapter() :
    PagingDataAdapter<KunjunganTokoDataItem, ItemRiwayatKunjunganAllAdapter.ViewHolder>(
        RiwayatKunjunganDiffUtill()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRiwayatKunjunganAllBinding.inflate(
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

    inner class ViewHolder(val binding: ItemRiwayatKunjunganAllBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KunjunganTokoDataItem?) {
            if (item != null) {
                binding.tvProdukTerjual.text = item.sisaProduk.toString()
                binding.tvTanggal.text = item.tanggal?.toTanggalIndonesiaInstant()
            }
        }
    }

    companion object {
        class RiwayatKunjunganDiffUtill : DiffUtil.ItemCallback<KunjunganTokoDataItem>() {
            override fun areItemsTheSame(
                oldItem: KunjunganTokoDataItem,
                newItem: KunjunganTokoDataItem
            ): Boolean {
                return oldItem.idKunjunganToko == newItem.idKunjunganToko
            }

            override fun areContentsTheSame(
                oldItem: KunjunganTokoDataItem,
                newItem: KunjunganTokoDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}