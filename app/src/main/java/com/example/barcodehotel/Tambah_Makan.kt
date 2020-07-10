package com.example.barcodehotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.barcodehotel.Model.FoodModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_makan.*

class Tambah_Makan : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    private var Nama: EditText? = null
    private var Harga: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_makan)

        Nama = findViewById<EditText>(R.id.txt_makanan)
        Harga = findViewById<EditText>(R.id.txt_harga)
        ref = FirebaseDatabase.getInstance().getReference("Makanan")

        btn_tambah.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val getNama: String = Nama?.getText().toString()
        val getHarga: String = Harga?.getText().toString()

        val user = FoodModel(getNama,getHarga)
        //val idMakanan = ref.push().key.toString()

        ref.push().setValue(user).addOnCompleteListener {
            Toast.makeText(this@Tambah_Makan, "Successs",Toast.LENGTH_SHORT).show()
        }

        val Intent = Intent (this, MainActivity::class.java)
        startActivity(Intent)
        finish()
    }
}
