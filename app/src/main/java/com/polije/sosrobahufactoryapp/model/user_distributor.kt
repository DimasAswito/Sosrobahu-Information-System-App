package com.polije.sosrobahufactoryapp.model
    import java.math.BigInteger
    import java.sql.Timestamp

data class user_distributor(
    val id_User_distributor: Int,
    val nama_lengkap: Char,
    val username: Char,
    val password: Char,
    val no_telp: Char,
    val status: Int,
    val level: Int,
    val gambar_ktp: Char,
    val nama_bank: Char,
    val no_rek: BigInteger,
    val provinsi: Char,
    val alamat: Char,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)
