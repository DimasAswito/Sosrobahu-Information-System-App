package com.polije.sosrobahufactoryapp.ui.sales.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.sales.OrderSalesDetailProdukItem
import com.polije.sosrobahufactoryapp.databinding.ItemDetailOrderAgenBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class DetailOrderSalesAdapter(private val items: List<OrderSalesDetailProdukItem>) :
    RecyclerView.Adapter<DetailOrderSalesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDetailOrderAgenBinding.inflate(
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
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.count()

    inner class ViewHolder(private val binding: ItemDetailOrderAgenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderSalesDetailProdukItem) {
            binding.tvNamaRokok.text = item.namaRokok
            binding.tvJumlahItem.text = item.quantity.toString()
            binding.tvHargaSatuan.text = item.hargaAgen?.toRupiah()

            if (item.hargaAgen != null && item.quantity != null) {
                binding.tvJumlahHarga.text = (item.hargaAgen * item.quantity).toRupiah()
            }

        }
    }
}