package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.RiwayatRestockItem
import com.polije.sosrobahufactoryapp.utils.toTanggalIndonesia

class RiwayatRestokAdapter(private val onRiwayatItemClicked: OnRiwayatItemClicked) :
    PagingDataAdapter<RiwayatRestockItem, RiwayatRestokAdapter.ViewHolder>(RiwayatRestokDiffUtill()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)

        //        val txtNamaProduk: TextView = itemView.findViewById(R.id.txtNamaProduk)
        val txtTanggalRestok: TextView = itemView.findViewById(R.id.txtTanggalRestok)
        val txtJumlahProduk: TextView = itemView.findViewById(R.id.txtJumlahProduk)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_restok, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = getItem(position)
        if (riwayat != null) {

            holder.txtTanggalRestok.text = riwayat?.tanggal?.toTanggalIndonesia()
            holder.txtJumlahProduk.text = riwayat?.jumlah

            holder.itemView.setOnClickListener { onRiwayatItemClicked.onItemClick(riwayat) }

        }
    }

    companion object {
        class RiwayatRestokDiffUtill : DiffUtil.ItemCallback<RiwayatRestockItem>() {
            override fun areItemsTheSame(
                oldItem: RiwayatRestockItem,
                newItem: RiwayatRestockItem
            ): Boolean {
                return oldItem.idRestock == newItem.idRestock
            }

            override fun areContentsTheSame(
                oldItem: RiwayatRestockItem,
                newItem: RiwayatRestockItem
            ): Boolean {
                return oldItem.idRestock == newItem.idRestock
            }

        }
    }

    interface OnRiwayatItemClicked {
        fun onItemClick(item: RiwayatRestockItem)
    }
}
