package com.polije.sosrobahufactoryapp.ui.distributor.order.component

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.databinding.ItemTambahOrderDistributorBinding
import com.polije.sosrobahufactoryapp.ui.distributor.order.pilihProdukDistributor.SelectedProdukDistributor
import com.polije.sosrobahufactoryapp.utils.toRupiah

class TambahOrderDistributorAdapter(
    private val onQuantityChangeListener: OnQuantityChangeListener
) : ListAdapter<SelectedProdukDistributor, TambahOrderDistributorAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemTambahOrderDistributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentWatcher: TextWatcher? = null
        private var isUpdatingText = false

        fun bind(item: SelectedProdukDistributor) {
            // Load image
            Glide.with(binding.root.context)
                .load(BuildConfig.PICTURE_BASE_URL + "produk/" + item.item.gambar)
                .error(R.drawable.logo)
                .into(binding.imgProduk)

            // Set name
            binding.txtNamaProduk.text = item.item.namaRokok
            binding.txtHargaSatuan.text = item.item.hargaKartonPabrik.toRupiah()

            // Manage TextWatcher
            removeCurrentTextWatcher()

            // Sync text if needed
            if (binding.edtJumlahProduk.text.toString() != item.quantity.toString()) {
                isUpdatingText = true
                binding.edtJumlahProduk.setText(item.quantity?.toString() ?: "")
                binding.edtJumlahProduk.setSelection(binding.edtJumlahProduk.text.length)
                isUpdatingText = false
            }

            attachNewTextWatcher(item)

            // Focus handling
            binding.edtJumlahProduk.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    item.hasFocus = hasFocus
                    if (hasFocus) {
                        item.cursorPosition = binding.edtJumlahProduk.selectionStart
                    }
                }

            // Restore focus & cursor
            if (item.hasFocus) {
                binding.edtJumlahProduk.requestFocus()
                if (item.cursorPosition in 0..binding.edtJumlahProduk.text.length) {
                    binding.edtJumlahProduk.setSelection(item.cursorPosition)
                }
            }
        }

        fun updateQuantityOnly(newQty: Int) {
            if (binding.edtJumlahProduk.text.toString() != newQty.toString()) {
                isUpdatingText = true
                binding.edtJumlahProduk.setText(newQty.toString())
                binding.edtJumlahProduk.setSelection(binding.edtJumlahProduk.text.length)
                isUpdatingText = false
            }
        }

        private fun removeCurrentTextWatcher() {
            currentWatcher?.let {
                binding.edtJumlahProduk.removeTextChangedListener(it)
                currentWatcher = null
            }
        }

        private fun attachNewTextWatcher(item: SelectedProdukDistributor) {
            val watcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (isUpdatingText) return
                    try {
                        val qty = s.toString().toIntOrNull() ?: 0
                        if (qty != item.quantity) {
                            item.cursorPosition = binding.edtJumlahProduk.selectionStart
                            onQuantityChangeListener.onQuantityChanged(item, qty)
                        }
                    } catch (e: Exception) {
                        Log.e("TambahOrderDistAdapter", "Error updating quantity", e)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
            currentWatcher = watcher
            binding.edtJumlahProduk.addTextChangedListener(watcher)
        }

        fun onViewRecycled() {
            removeCurrentTextWatcher()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTambahOrderDistributorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, emptyList())
        } else {
            val newQty = payloads[0] as Int
            holder.updateQuantityOnly(newQty)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    class DiffCallback : DiffUtil.ItemCallback<SelectedProdukDistributor>() {
        override fun areItemsTheSame(
            oldItem: SelectedProdukDistributor,
            newItem: SelectedProdukDistributor
        ): Boolean = oldItem.item.idMasterBarang == newItem.item.idMasterBarang

        override fun areContentsTheSame(
            oldItem: SelectedProdukDistributor,
            newItem: SelectedProdukDistributor
        ): Boolean = oldItem.quantity == newItem.quantity && oldItem.item == newItem.item

        override fun getChangePayload(
            oldItem: SelectedProdukDistributor,
            newItem: SelectedProdukDistributor
        ): Any? = if (oldItem.quantity != newItem.quantity) newItem.quantity else null
    }

    interface OnQuantityChangeListener {
        fun onQuantityChanged(item: SelectedProdukDistributor, newQty: Int)
    }
}
