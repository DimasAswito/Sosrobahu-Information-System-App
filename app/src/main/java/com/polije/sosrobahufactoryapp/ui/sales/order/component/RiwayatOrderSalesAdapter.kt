package com.polije.sosrobahufactoryapp.ui.sales.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDataItem

import com.polije.sosrobahufactoryapp.databinding.ItemRiwayatOrderAgenBinding

class RiwayatOrderSalesAdapter(val riwayatOrderSalesAction: RiwayatOrderSalesAction) :
    PagingDataAdapter<OrderSalesDataItem, RiwayatOrderSalesAdapter.ViewHolder>(
        RiwayatOrderSalesDiffUtil()
    ) {
    inner class ViewHolder(val binding: ItemRiwayatOrderAgenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderSalesDataItem) {
            binding.txtTanggalPesanan.text = item.tanggal
            binding.txtJumlahProduk.text = item.jumlah.toString()

            binding.cardBackground.isSelected = item.statusPemesanan == 1
            binding.cardBackground.isActivated = item.statusPemesanan == 2

            binding.root.setOnClickListener {
                riwayatOrderSalesAction.onRiwayatOrderItemClicked(item)
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
        class RiwayatOrderSalesDiffUtil :
            DiffUtil.ItemCallback<OrderSalesDataItem>() {
            override fun areItemsTheSame(
                oldItem: OrderSalesDataItem,
                newItem: OrderSalesDataItem
            ): Boolean {
                return oldItem.idOrder == newItem.idOrder
            }

            override fun areContentsTheSame(
                oldItem: OrderSalesDataItem,
                newItem: OrderSalesDataItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface RiwayatOrderSalesAction {
        fun onRiwayatOrderItemClicked(order: OrderSalesDataItem)
    }
}