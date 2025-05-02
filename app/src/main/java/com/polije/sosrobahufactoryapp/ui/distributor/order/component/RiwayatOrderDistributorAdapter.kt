package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemRiwayatOrderDistributorBinding

class RiwayatOrderDistributorAdapter(val riwayatOrderDistributorAction: RiwayatOrderDistributorAction) :
    PagingDataAdapter<RiwayatOrderDistributorDataItem, RiwayatOrderDistributorAdapter.ViewHolder>(
        RiwayatOrderDistributorDiffUtil()
    ) {
    inner class ViewHolder(val binding: ItemRiwayatOrderDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatOrderDistributorDataItem) {
            binding.txtTanggalPesanan.text = item.tanggal
            binding.txtJumlahProduk.text = item.jumlah.toString()
            binding.root.setOnClickListener {
                riwayatOrderDistributorAction.onRiwayatOrderItemClicked(item.idOrder ?: 0)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRiwayatOrderDistributorBinding.inflate(
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
        class RiwayatOrderDistributorDiffUtil :
            DiffUtil.ItemCallback<RiwayatOrderDistributorDataItem>() {
            override fun areItemsTheSame(
                oldItem: RiwayatOrderDistributorDataItem,
                newItem: RiwayatOrderDistributorDataItem
            ): Boolean {
                return oldItem.idOrder == newItem.idOrder
            }

            override fun areContentsTheSame(
                oldItem: RiwayatOrderDistributorDataItem,
                newItem: RiwayatOrderDistributorDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface RiwayatOrderDistributorAction {
        fun onRiwayatOrderItemClicked(idOrder : Int)
    }
}