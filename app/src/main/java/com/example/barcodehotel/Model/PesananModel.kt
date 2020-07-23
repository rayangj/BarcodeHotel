package com.example.barcodehotel.Model

class PesananModel(
    var id : String,
    var nama : String,
    var harga : String,
    var jumlah : String,
    var total: String,
    var finalTotal: String,
    var tanggal: String

) {
    constructor() : this("","","","","","","")
}