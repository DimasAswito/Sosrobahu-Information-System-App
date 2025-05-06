package com.polije.sosrobahufactoryapp.ui.distributor.pesanan.component

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.PesananMasukDistributorDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemPesananDistributorBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class PesananDistributorAdapter(val pesananDistributorAction: PesananDistributorAction) :
    PagingDataAdapter<PesananMasukDistributorDataItem, PesananDistributorAdapter.ViewHolder>(
        PesananDistributorDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPesananDistributorBinding.inflate(
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

    inner class ViewHolder(val binding: ItemPesananDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PesananMasukDistributorDataItem?) {
            if (item != null) {
                binding.tvAgen.text = item.namaAgen
                binding.tvTotalHarga.text = item.total?.toRupiah()

                // ✅ Tambahan untuk status
                val status = when (item.statusPemesanan) {
                    0 -> "Diproses"
                    1 -> "Selesai"
                    2 -> "Ditolak"
                    else -> "Tidak Diketahui"
                }

                binding.tvStatus.text = status
                binding.tvStatus.isSelected = status == "Selesai"
                binding.tvStatus.isActivated = status == "Ditolak"
                binding.tvStatus.setBackgroundResource(R.drawable.status_background)

                binding.root.setOnClickListener {
                    pesananDistributorAction.onItemClicked(item)
                }
            }
        }
    }


    interface PesananDistributorAction {
        fun onItemClicked(item: PesananMasukDistributorDataItem)
    }

    companion object {
        class PesananDistributorDiffUtil :
            DiffUtil.ItemCallback<PesananMasukDistributorDataItem>() {
            override fun areItemsTheSame(
                oldItem: PesananMasukDistributorDataItem,
                newItem: PesananMasukDistributorDataItem
            ): Boolean {
                return oldItem.idOrder == newItem.idOrder
            }

            override fun areContentsTheSame(
                oldItem: PesananMasukDistributorDataItem,
                newItem: PesananMasukDistributorDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}