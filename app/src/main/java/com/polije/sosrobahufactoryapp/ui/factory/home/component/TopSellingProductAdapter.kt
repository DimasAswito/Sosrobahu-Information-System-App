package com.polije.sosrobahufactoryapp.ui.factory.home.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.model.TopSellingProduct

class TopSellingProductAdapter(private val productList: List<TopSellingProduct>) :
    RecyclerView.Adapter<TopSellingProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRank: TextView = view.findViewById(R.id.tvRank)
        val imgProduct: ImageView = view.findViewById(R.id.topProductImage)
        val tvProductName: TextView = view.findViewById(R.id.topProductName)
        val tvRevenue: TextView = view.findViewById(R.id.topProductRevenue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_selling_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.tvRank.text = "${product.rank}."
        holder.tvProductName.text = product.name
        holder.tvRevenue.text = "Rp ${product.revenue}"

        val imageResId = holder.itemView.context.resources.getIdentifier(
            product.image, "drawable", holder.itemView.context.packageName
        )
        if (imageResId != 0) {
            holder.imgProduct.setImageResource(imageResId)
        } else {
            holder.imgProduct.setImageResource(R.drawable.logo) // Placeholder jika gambar tidak ditemukan
        }
    }

    override fun getItemCount(): Int = productList.size
}
