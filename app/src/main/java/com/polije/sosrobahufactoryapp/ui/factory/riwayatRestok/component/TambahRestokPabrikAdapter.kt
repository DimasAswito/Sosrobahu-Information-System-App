package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

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
import com.polije.sosrobahufactoryapp.databinding.ItemTambahRestokBinding
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestokPabrik

class TambahRestokPabrikAdapter(
    private val onQuantityChangeListener: OnQuantityChangeListener,
) : ListAdapter<SelectedProdukRestokPabrik, TambahRestokPabrikAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemTambahRestokBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentWatcher: TextWatcher? = null
        private var isUpdatingText = false

        fun bind(produk: SelectedProdukRestokPabrik) {

            val restokGambar = BuildConfig.PICTURE_BASE_URL + "produk/" + produk.item.gambar
            Glide.with(itemView.context)
                .load(restokGambar)
                .placeholder(R.drawable.logo)
                .error(R.drawable.rokok)
                .into(binding.imgProduk)

            // Set product data
            binding.apply {
                txtNamaProduk.text = produk.item.namaRokok

                // Handle quantity updates with clean TextWatcher management
                removeCurrentTextWatcher()

                // Set text only if different - prevents unnecessary updates
                if (edtJumlahProduk.text.toString() != produk.quantity.toString()) {
                    isUpdatingText = true
                    edtJumlahProduk.setText(produk.quantity.toString())
                    // Fix cursor position to end of text
                    edtJumlahProduk.setSelection(edtJumlahProduk.text.length)
                    isUpdatingText = false
                }

                attachNewTextWatcher(produk)

                // Set date if available
//                if (produk.tanggal.isNotEmpty()) {
//                    txtPilihTanggal.text = produk.tanggal
//                } else {
//                    txtPilihTanggal.text = "Pilih Tanggal"
//                }
//
//                // Set up date picker
//                txtPilihTanggal.setOnClickListener {
//                    showDatePicker(produk)
//                }
            }

            // Fokus management - simpan posisi dan fokus input
            binding.edtJumlahProduk.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    produk.hasFocus = hasFocus
                    if (hasFocus) {
                        // Simpan posisi kursor ketika dapat fokus
                        produk.cursorPosition = binding.edtJumlahProduk.selectionStart
                    }
                }

            // Restore fokus dan posisi kursor jika diperlukan
            if (produk.hasFocus) {
                binding.edtJumlahProduk.requestFocus()
                if (produk.cursorPosition >= 0 && produk.cursorPosition <= binding.edtJumlahProduk.text.length) {
                    binding.edtJumlahProduk.setSelection(produk.cursorPosition)
                }
            }


        }

        fun updateQuantityOnly(newQty: Int) {
            // programmatically update text without re-attaching watchers or losing focus
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

        private fun attachNewTextWatcher(produk: SelectedProdukRestokPabrik) {
            val watcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Skip if we're programmatically updating the text
                    if (isUpdatingText) return

                    try {
                        val qty = s.toString().toIntOrNull() ?: 0
                        if (qty != produk.quantity) {
                            // Simpan posisi kursor sebelum perubahan
                            produk.cursorPosition = binding.edtJumlahProduk.selectionStart

                            // Update data
                            onQuantityChangeListener.onQuantityChanged(produk, qty)
                        }
                    } catch (e: Exception) {
                        Log.e("TambahRestokAdapter", "Error updating quantity", e)
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
        val binding = ItemTambahRestokBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any?>) {
        if (payloads.isEmpty()) {
            // full re-bind
            super.onBindViewHolder(holder, position, emptyList<SelectedProdukRestokPabrik>())
        } else {
            // partial – only quantity changed
            val newQty = payloads[0] as Int
            holder.updateQuantityOnly(newQty)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    class DiffCallback : DiffUtil.ItemCallback<SelectedProdukRestokPabrik>() {
        override fun areItemsTheSame(
            oldItem: SelectedProdukRestokPabrik,
            newItem: SelectedProdukRestokPabrik
        ): Boolean {
            return oldItem.item.idMasterBarang == newItem.item.idMasterBarang
        }

        override fun areContentsTheSame(
            oldItem: SelectedProdukRestokPabrik,
            newItem: SelectedProdukRestokPabrik
        ): Boolean {
            // Jangan bandingkan hasFocus dan cursorPosition di sini
            // untuk mencegah recycling yang tidak perlu
            return oldItem.quantity == newItem.quantity &&

                    oldItem.item == newItem.item
        }

        override fun getChangePayload(
            oldItem: SelectedProdukRestokPabrik,
            newItem: SelectedProdukRestokPabrik
        ): Any? {
            return if (oldItem.quantity != newItem.quantity) newItem.quantity else null
        }
    }

    interface OnQuantityChangeListener {
        fun onQuantityChanged(produk: SelectedProdukRestokPabrik, newQty: Int)
    }


}


