package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.distributor.PilihBarangPabrikDistributorResponseItem
import com.polije.sosrobahufactoryapp.databinding.ItemPilihProdukDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor

class PilihProdukDistributorAdapter(
    private var produkList: List<PilihBarangPabrikDistributorResponseItem>,
    private val selectedList: MutableList<SelectedProdukDistributor>,
    private val onItemSelected: (PilihBarangPabrikDistributorResponseItem, Boolean) -> Unit
) : RecyclerView.Adapter<PilihProdukDistributorAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPilihProdukDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = CircularProgressDrawable(binding.root.context).apply {
            strokeWidth = 6f
            centerRadius = 30f
            start()
        }

        fun bind(item: PilihBarangPabrikDistributorResponseItem) {
            Glide.with(binding.root.context)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.gambar)
                .placeholder(progressDrawable)
                .error(R.drawable.logo)
                .into(binding.imgProduk)

            binding.txtNamaProduk.text = item.namaRokok

            val isSelected = selectedList.any { it.item.idMasterBarang == item.idMasterBarang }

            binding.cardProduk.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (isSelected) R.color.abu_abu_cerah else android.R.color.white
                )
            )

            binding.root.setOnClickListener {
                val baruDipilih = !isSelected
                if (baruDipilih) {
                    selectedList.add(SelectedProdukDistributor(item))
                } else {
                    selectedList.removeAll { it.item.idMasterBarang == item.idMasterBarang }
                }

                notifyItemChanged(adapterPosition)
                onItemSelected(item, baruDipilih)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPilihProdukDistributorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(produkList[position])
    }

    override fun getItemCount(): Int = produkList.size

    fun updateList(newList: List<PilihBarangPabrikDistributorResponseItem>) {
        produkList = newList
        notifyDataSetChanged()
    }
}
