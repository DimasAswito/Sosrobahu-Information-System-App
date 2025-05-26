import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardDistributorResponse(
    @SerialName("produkData")
    val produkData: List<ProdukDataItem> = emptyList(),

    @SerialName("finalStockKarton")
    val finalStockKarton: Int = 0,

    @SerialName("totalPendapatan")
    val totalPendapatan: Long = 0L,

    @SerialName("topProductName")
    val topProductName: String = "",

    @SerialName("totalAgen")
    val totalAgen: Int = 0,

    /**
     * Key is the month string (e.g. "2025-03")
     */
    @SerialName("pesananPerBulan")
    val pesananPerBulan: Map<String, PesananDistributorPerBulan> = emptyMap(),

    @SerialName("availableYears")
    val availableYears: List<Int> = emptyList(),

    @SerialName("nama_distributor")
    val namaDistributor: String = ""
)

@Serializable
data class ProdukDataItem(
    @SerialName("nama_rokok")
    val namaRokok: String? = "",

    @SerialName("gambar")
    val gambar: String? = "",

    @SerialName("total_produk")
    val totalProduk: Int = 0
)

@Serializable
data class PesananDistributorPerBulan(
    @SerialName("pesanan")
    val pesanan: List<PesananItem> = emptyList(),

    @SerialName("total_omset")
    val totalOmset: Long = 0L,

    @SerialName("total_karton")
    val totalKarton: Int = 0
)

@Serializable
data class PesananItem(
    @SerialName("created_at")
    val createdAt: String = "",

    @SerialName("updated_at")
    val updatedAt: String = "",

    @SerialName("id_order")
    val idOrder: Int = 0,

    @SerialName("id_user_agen")
    val idUserAgen: Int = 0,

    @SerialName("id_user_distributor")
    val idUserDistributor: Int = 0,

    @SerialName("jumlah")
    val jumlah: Int = 0,

    @SerialName("total")
    val total: Long = 0L,

    @SerialName("tanggal")
    val tanggal: String = "",

    @SerialName("bukti_transfer")
    val buktiTransfer: String = "",

    @SerialName("status_pemesanan")
    val statusPemesanan: Int = 0
)
