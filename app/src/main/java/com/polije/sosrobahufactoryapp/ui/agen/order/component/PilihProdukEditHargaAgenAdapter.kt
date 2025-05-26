package com.polije.sosrobahufactoryapp.ui.agen.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.agen.RokokAgensItem
import com.polije.sosrobahufactoryapp.databinding.ItemPengaturanHargaAgenBinding
import com.polije.sosrobahufactoryapp.utils.toRupiah

class PilihProdukEditHargaAgenAdapter(private val action: PengaturanHargaAgenAction) :
    ListAdapter<RokokAgensItem, PilihProdukEditHargaAgenAdapter.ViewHolder>(
        PengaturanHargaDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPengaturanHargaAgenBinding.inflate(
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

    inner class ViewHolder(val binding: ItemPengaturanHargaAgenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = CircularProgressDrawable(binding.root.context).apply {
            strokeWidth = 6f
            centerRadius = 30f
            start()
        }

        fun bind(item: RokokAgensItem) {
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
        class PengaturanHargaDiffUtil : DiffUtil.ItemCallback<RokokAgensItem>() {
            override fun areItemsTheSame(
                oldItem: RokokAgensItem,
                newItem: RokokAgensItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RokokAgensItem,
                newItem: RokokAgensItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface PengaturanHargaAgenAction {
        fun onPengaturanHargaItemClicked(item: RokokAgensItem)
    }
}