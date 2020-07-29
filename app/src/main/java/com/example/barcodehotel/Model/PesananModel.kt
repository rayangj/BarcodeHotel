package com.example.barcodehotel.Model

class PesananModel(
    var id : String,
    var nama : String,
    var nokamar : String,
    var status : String,
    var jumlah : String,
    var total: String,
    var finalTotal: String,
    var tanggal: String,
    var jam: String

) {
    constructor() : this("","","","","","","","","")
}