package com.example.barcodehotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.KeranjangAdapter
import com.example.barcodehotel.Model.KeranjangModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_keranjang.*
import kotlinx.android.synthetic.main.fragment_food.*

class KeranjangActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<KeranjangModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)
        title = "Keranjang"

        btn_pesan.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        getData()
    }
    private fun getData(){
        Toast.makeText(this, "Mohon tunggu sebentar...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()

        //val keyy = ref.child("Kamar").child("01").child("Keranjang").push().getKey()
        ref.child("Kamar").child("01").child("Keranjang").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@KeranjangActivity, "Data tidak tampil", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                //val keyy2 = ref.child("Makanan").push().getKey()
                listView= java.util.ArrayList<KeranjangModel>()
                for (dataSnapshot in p0.children ) {
                    val teman = dataSnapshot.getValue(KeranjangModel::class.java)
                    listView.add(teman!!)
                }
                rv_View_keranjang.layoutManager = LinearLayoutManager(this@KeranjangActivity)
                rv_View_keranjang.adapter = KeranjangAdapter(this@KeranjangActivity,listView)
                Toast.makeText(this@KeranjangActivity, "Data Berhasil Dimuat", Toast.LENGTH_LONG).show()
            }

        })
    }
}
