package com.polije.sosrobahufactoryapp.ui.sales.daftarToko.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.data.model.sales.KunjunganTokoSalesItem
import com.polije.sosrobahufactoryapp.databinding.ItemRiwayatKunjunganBinding

class ItemRiwayatKunjunganAdapter(
    private val items: List<KunjunganTokoSalesItem>,
    private val action: OnItemRiwayatKunjunganAction
) :
    RecyclerView.Adapter<ItemRiwayatKunjunganAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemRiwayatKunjunganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(private val binding: ItemRiwayatKunjunganBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KunjunganTokoSalesItem) {
            binding.tvTanggal.text = item.tanggal
            binding.tvProdukTerjual.text = item.sisaProduk.toString()
            binding.btnDelete.setOnClickListener {
                action.onItemRiwayatKunjunganClicked(item.idKunjunganToko ?: 0)
            }
        }
    }

    interface OnItemRiwayatKunjunganAction {
        fun onItemRiwayatKunjunganClicked(idKunjungan: Int)
    }
}