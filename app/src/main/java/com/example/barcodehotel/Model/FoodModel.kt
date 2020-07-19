package com.example.barcodehotel.Model

data class FoodModel(
    var id: String,
    var nama : String,
    var harga : String,
    var stok: String,
    var kategori: String,
    var gambar: String

) {
    constructor() : this("","","", "","","")
}