package com.polije.sosrobahufactoryapp.ui.agen.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.agen.RiwayatOrderAgenDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemRiwayatOrderAgenBinding

class RiwayatOrderAgenAdapter(val riwayatOrderAgenAction: RiwayatOrderAgenAction) :
    PagingDataAdapter<RiwayatOrderAgenDataItem, RiwayatOrderAgenAdapter.ViewHolder>(
        RiwayatOrderAgenDiffUtil()
    ) {
    inner class ViewHolder(val binding: ItemRiwayatOrderAgenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatOrderAgenDataItem) {
            binding.txtTanggalPesanan.text = item.tanggal
            binding.txtJumlahProduk.text = item.jumlah.toString()

            binding.cardBackground.isSelected = item.statusPemesanan == 1
            binding.cardBackground.isActivated = item.statusPemesanan == 2

            binding.root.setOnClickListener {
                riwayatOrderAgenAction.onRiwayatOrderItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRiwayatOrderAgenBinding.inflate(
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
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        class RiwayatOrderAgenDiffUtil :
            DiffUtil.ItemCallback<RiwayatOrderAgenDataItem>() {
            override fun areItemsTheSame(
                oldItem: RiwayatOrderAgenDataItem,
                newItem: RiwayatOrderAgenDataItem
            ): Boolean {
                return oldItem.idOrder == newItem.idOrder
            }

            override fun areContentsTheSame(
                oldItem: RiwayatOrderAgenDataItem,
                newItem: RiwayatOrderAgenDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface RiwayatOrderAgenAction {
        fun onRiwayatOrderItemClicked(order: RiwayatOrderAgenDataItem)
    }
}