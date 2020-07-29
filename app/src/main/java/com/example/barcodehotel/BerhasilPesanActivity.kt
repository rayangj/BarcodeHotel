package com.example.barcodehotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_berhasil_pesan.*

class BerhasilPesanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berhasil_pesan)

        btn_lih_his.setOnClickListener {
            val intent = Intent(this@BerhasilPesanActivity, HistoryPesananActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_kem_ke_hom.setOnClickListener {
//            val intent = Intent(this@BerhasilPesanActivity, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }
    }
}
