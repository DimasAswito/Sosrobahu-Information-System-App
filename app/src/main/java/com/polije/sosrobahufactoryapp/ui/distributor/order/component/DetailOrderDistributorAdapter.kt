package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.distributor.RiwayatOrderDistributorDetailProdukItem
import com.polije.sosrobahufactoryapp.databinding.ItemDetailOrderDistributorBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class DetailOrderDistributorAdapter(val item: List<RiwayatOrderDistributorDetailProdukItem>) :
    RecyclerView.Adapter<DetailOrderDistributorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDetailOrderDistributorBinding.inflate(
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
        val item = item[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = item.count()

    inner class ViewHolder(private val binding: ItemDetailOrderDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatOrderDistributorDetailProdukItem) {
            binding.tvNamaRokok.text = item.namaRokok
            binding.tvJumlahItem.text = item.quantity.toString()
            binding.tvHargaSatuan.text = item.hargaKartonPabrik?.toRupiah()

            if (item.hargaKartonPabrik != null && item.quantity != null) {
                binding.tvJumlahHarga.text = (item.hargaKartonPabrik * item.quantity).toRupiah()
            }

        }
    }
}