package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.DistributorBarangItems
import com.polije.sosrobahufactoryapp.databinding.ItemPengaturanHargaDistributorBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class PilihProdukEditHargaAdapter(val action: PengaturanHargaAction) :
    ListAdapter<DistributorBarangItems, PilihProdukEditHargaAdapter.ViewHolder>(PengaturanHargaDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPengaturanHargaDistributorBinding.inflate(
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

    inner class ViewHolder(val binding: ItemPengaturanHargaDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = CircularProgressDrawable(binding.root.context).apply {
            strokeWidth = 6f
            centerRadius = 30f
            start()
        }

        fun bind(item: DistributorBarangItems) {
            binding.tvNamaProduk.text = item.namaRokok

            Glide.with(binding.root.context)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.gambar)
                .placeholder(progressDrawable)
                .error(R.drawable.logo)
                .into(binding.imgProduk)

            binding.tvHargaPabrik.text = "Harga Pabrik : " + item.hargaPabrik.toRupiah()
            binding.tvHargaDistributor.text = "Harga Distributor : " + item.harga.toRupiah()


            binding.root.setOnClickListener {
                action.onPengaturanHargaItemClicked(item)
            }
        }
    }

    companion object {
        class PengaturanHargaDiffUtil : DiffUtil.ItemCallback<DistributorBarangItems>() {
            override fun areItemsTheSame(
                oldItem: DistributorBarangItems,
                newItem: DistributorBarangItems
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DistributorBarangItems,
                newItem: DistributorBarangItems
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface PengaturanHargaAction {
        fun onPengaturanHargaItemClicked(item: DistributorBarangItems)
    }
}