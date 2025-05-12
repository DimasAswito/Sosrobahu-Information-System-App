package com.polije.sosrobahufactoryapp.ui.sales.order.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.sales.ListBarangAgenSalesDataItem
import com.polije.sosrobahufactoryapp.databinding.ItemPilihProdukDistributorBinding
import com.polije.sosrobahufactoryapp.ui.sales.order.pilihProdukSales.SelectedProdukSales

class PilihProdukSalesAdapter(
    private var produkList: List<ListBarangAgenSalesDataItem>,
    private val selectedList: MutableList<SelectedProdukSales>,
    private val onItemSelected: (ListBarangAgenSalesDataItem, Boolean) -> Unit
) : RecyclerView.Adapter<PilihProdukSalesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPilihProdukDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressDrawable = CircularProgressDrawable(binding.root.context).apply {
            strokeWidth = 6f
            centerRadius = 30f
            start()
        }

        fun bind(item: ListBarangAgenSalesDataItem) {
            Glide.with(binding.root.context)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.gambar)
                .placeholder(progressDrawable)
                .error(R.drawable.logo)
                .into(binding.imgProduk)

            binding.txtNamaProduk.text = item.namaRokok

            val isSelected = selectedList.any { it.item.idBarangAgen == item.idBarangAgen }

            binding.cardProduk.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (isSelected) R.color.abu_abu_cerah else android.R.color.white
                )
            )

            binding.root.setOnClickListener {
                val baruDipilih = !isSelected
                if (baruDipilih) {
                    selectedList.add(SelectedProdukSales(item))
                } else {
                    selectedList.removeAll { it.item.idBarangAgen == item.idBarangAgen }
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

    fun updateList(newList: List<ListBarangAgenSalesDataItem>) {
        produkList = newList
        notifyDataSetChanged()
    }
}