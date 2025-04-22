package com.polije.sosrobahufactoryapp.ui.factory.home.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.polije.sosrobahufactoryapp.BuildConfig.PICTURE_BASE_URL
import com.polije.sosrobahufactoryapp.R
import com.polije.sosrobahufactoryapp.data.model.TopSellingProduct

class TopSellingProductPabrikAdapter :
    ListAdapter<TopSellingProduct, TopSellingProductPabrikAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRank: TextView = view.findViewById(R.id.tvRank)
        val imgProduct: ImageView = view.findViewById(R.id.topProductImage)
        val tvProductName: TextView = view.findViewById(R.id.topProductName)
        val tvProductStock: TextView = view.findViewById(R.id.topProductStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_selling_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.tvRank.text = "${product.rank}."
        holder.tvProductName.text = product.name
        holder.tvProductStock.text = "${product.stock} karton"

        val circularProgressDrawable = CircularProgressDrawable(holder.itemView.context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

        val imageUrl = PICTURE_BASE_URL + "produk/" + product.image
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.rokok)
            .into(holder.imgProduct)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopSellingProduct>() {
            override fun areItemsTheSame(
                oldItem: TopSellingProduct,
                newItem: TopSellingProduct
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: TopSellingProduct, newItem: TopSellingProduct
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
