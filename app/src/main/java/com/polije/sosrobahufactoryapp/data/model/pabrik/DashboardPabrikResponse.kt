import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarangPabrik(
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("id_master_barang") val idMasterBarang: Int,
    @SerialName("nama_rokok") val namaRokok: String,
    @SerialName("harga_karton_pabrik") val hargaKartonPabrik: Int,
    @SerialName("gambar") val gambar: String,
    @SerialName("stok_slop") val stokSlop: Int
)

@Serializable
data class Pesanan(
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("id_order") val idOrder: Int,
    @SerialName("id_user_distributor") val idUserDistributor: Int,
    @SerialName("jumlah") val jumlah: Int,
    @SerialName("total") val total: Int,
    @SerialName("tanggal") val tanggal: String,
    @SerialName("bukti_transfer") val buktiTransfer: String,
    @SerialName("status_pemesanan") val statusPemesanan: Int
)

@Serializable
data class PesananPerBulan(
    @SerialName("pesanan") val pesanan: List<Pesanan>,
    @SerialName("total_omset") val totalOmset: Long,
    @SerialName("total_karton") val totalKarton: Int
)

@Serializable
data class DashboardResponse(
    @SerialName("barangPabriks") val barangPabriks: List<BarangPabrik>,
    @SerialName("namaRokokList") val namaRokokList: List<String>,
    @SerialName("gambarRokokList") val gambarRokokList: List<String>,
    @SerialName("totalProdukList") val totalProdukList: List<Int>,
    @SerialName("finalStockKarton") val finalStockKarton: Int,
    @SerialName("totalPendapatan") val totalPendapatan: String,
    @SerialName("topProductName") val topProductName: String,
    @SerialName("totalDistributor") val totalDistributor: Int,
    @SerialName("pesananPerbulan") val pesananPerbulan: Map<String, PesananPerBulan>
)
