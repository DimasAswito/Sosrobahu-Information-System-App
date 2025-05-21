package com.polije.sosrobahufactoryapp.ui.agen.pesanan.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.agen.PesananMasukAgenDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemPesananAgenBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class PesananAgenAdapter(val pesananAgenAction: PesananAgenAction) :
    PagingDataAdapter<PesananMasukAgenDataItem, PesananAgenAdapter.ViewHolder>(
        PesananAgenDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPesananAgenBinding.inflate(
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

    inner class ViewHolder(val binding: ItemPesananAgenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PesananMasukAgenDataItem?) {
            if (item != null) {
                binding.tvAgen.text = item.namaSales
                binding.tvTotalHarga.text = item.total?.toRupiah()
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
                    pesananAgenAction.onItemClicked(item)
                }
            }
        }
    }


    interface PesananAgenAction {
        fun onItemClicked(item: PesananMasukAgenDataItem)
    }

    companion object {
        class PesananAgenDiffUtil :
            DiffUtil.ItemCallback<PesananMasukAgenDataItem>() {
            override fun areItemsTheSame(
                oldItem: PesananMasukAgenDataItem,
                newItem: PesananMasukAgenDataItem
            ): Boolean {
                return oldItem.idOrder == newItem.idOrder
            }

            override fun areContentsTheSame(
                oldItem: PesananMasukAgenDataItem,
                newItem: PesananMasukAgenDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}