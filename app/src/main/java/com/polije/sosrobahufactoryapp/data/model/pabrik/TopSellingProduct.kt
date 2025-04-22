package com.polije.sosrobahufactoryapp.data.model.pabrik

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class TopSellingProduct(
    val rank: Int,
    val name: String,
    val image: String,
    val stock: Int
): Parcelable

@Parcelize
data class ListTopSellingProduct(
    val listTopSellingProduct: List<TopSellingProduct>
): Parcelable


