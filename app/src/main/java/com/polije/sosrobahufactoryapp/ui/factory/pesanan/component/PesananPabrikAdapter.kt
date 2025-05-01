package com.polije.sosrobahufactoryapp.ui.factory.pesanan.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.pabrik.PesananMasukItem
import com.polije.sosrobahufactoryapp.utils.toRupiah

class PesananPabrikAdapter(val onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<PesananMasukItem, PesananPabrikAdapter.PesananViewHolder>(PesananMasukDiffCallBack()) {

    class PesananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistributor: TextView = itemView.findViewById(R.id.tvDistributor)

        //        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvTotalHarga: TextView = itemView.findViewById(R.id.tvTotalHarga)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan, parent, false)
        return PesananViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        val pesanan = getItem(position)
        if (pesanan != null) {

            holder.tvDistributor.text = pesanan?.namaDistributor
//        holder.tvTanggal.text = pesanan.tanggal
            holder.tvTotalHarga.text = pesanan.total?.toRupiah()
//
            val status = when (pesanan?.statusPemesanan) {
                0 -> "Diproses"
                1 -> "Selesai"
                2 -> "Ditolak"
                else -> {
                    ""
                }
            }
            // Atur status dengan warna
            holder.tvStatus.text = status
            holder.tvStatus.isSelected = status == "Selesai"
            holder.tvStatus.isActivated = status == "Ditolak"
            holder.tvStatus.setBackgroundResource(R.drawable.status_background)
//
//
//        // Klik item untuk navigasi ke DetailPesananFragment dengan Bundle
            holder.itemView.setOnClickListener {
                onItemClickListener.onItemClick(pesanan)

            }

        }


    }

    companion object {
        class PesananMasukDiffCallBack : DiffUtil.ItemCallback<PesananMasukItem>() {
            override fun areItemsTheSame(
                oldItem: PesananMasukItem,
                newItem: PesananMasukItem
            ): Boolean {
                return oldItem.idOrder == newItem.idOrder
            }

            override fun areContentsTheSame(
                oldItem: PesananMasukItem,
                newItem: PesananMasukItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(pesanan: PesananMasukItem)
    }
}


