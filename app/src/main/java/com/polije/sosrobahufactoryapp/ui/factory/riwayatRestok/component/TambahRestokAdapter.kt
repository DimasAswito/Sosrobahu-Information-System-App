package com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.component

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.ProdukRestok
import com.polije.sosrobahufactoryapp.ui.factory.riwayatRestok.pilihProdukRestok.SelectedProdukRestok
import java.util.Calendar

class TambahRestokAdapter(private val produkList: List<SelectedProdukRestok>) :
    RecyclerView.Adapter<TambahRestokAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduk: ImageView = itemView.findViewById(R.id.imgProduk)
        val txtNamaProduk: TextView = itemView.findViewById(R.id.txtNamaProduk)
        val edtJumlahProduk: EditText = itemView.findViewById(R.id.edtJumlahProduk)
        val txtPilihTanggal: TextView = itemView.findViewById(R.id.txtPilihTanggal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tambah_restok, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = produkList[position]

//        holder.imgProduk.setImageResource(produk)
        holder.txtNamaProduk.text = produk.item.namaRokok
        holder.edtJumlahProduk.setText(produk.quantity.toString())
//        holder.txtPilihTanggal.text =
//            if (produk.item..isNotEmpty()) produk.tanggal else "Pilih Tanggal"

        // Menyimpan jumlah produk saat diubah
        holder.edtJumlahProduk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                produk.quantity = s.toString().toIntOrNull() ?: 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Memilih tanggal
        holder.txtPilihTanggal.setOnClickListener {
            val context = it.context
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val tanggal = "$dayOfMonth/${month + 1}/$year"

                    holder.txtPilihTanggal.text = tanggal
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    override fun getItemCount(): Int = produkList.size
}
