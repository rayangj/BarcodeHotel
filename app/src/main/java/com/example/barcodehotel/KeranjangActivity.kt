package com.example.barcodehotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.KeranjangAdapter
import com.example.barcodehotel.Model.KeranjangModel
import com.example.barcodehotel.Model.PesananModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_keranjang.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class KeranjangActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<KeranjangModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)
        title = "Keranjang"


        getData()
    }
    private fun getData(){
        Toast.makeText(this, "Mengambil Data...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()

        ref.child("Kamar").child("01").child("Keranjang").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@KeranjangActivity, "Data tidak tampil", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                listView= java.util.ArrayList<KeranjangModel>()
                var subTotal = 0
                val idpsn = ref.push().key.toString()
                var nama = ""
                var harga = ""
                var jumlah = ""
                var total = ""
                val tanggal = SimpleDateFormat("EEE, dd MMM yyyy")
                val cTanggal = tanggal.format(Date())
                val jam = SimpleDateFormat("HH:mm")
                val cJam = jam.format(Date())

                for (dataSnapshot in p0.children ) {
                    val teman = dataSnapshot.getValue(KeranjangModel::class.java)
                        val totalItem = teman?.total!!.toInt()
                        val jml = Integer.valueOf(totalItem)
                        subTotal = subTotal + jml

                        val name = teman.nama.toString()
                        val harge = teman.harga.toString()
                        val jumleh = teman.jumlah.toString()
                        val totel = teman.total.toString()
                        val finalTotal = subTotal.toString()
                        btn_pesan.text = "Rp." + finalTotal + " - Pesan"

                        nama = "$nama$name-"
                        harga = "$harga$harge-"
                        jumlah = "$jumlah$jumleh-"
                        total = "$total$totel-"

                        btn_pesan.setOnClickListener {
                            val pesan = PesananModel(idpsn, nama, harga, jumlah, total,finalTotal, "$cTanggal $cJam")
                            ref.child("Pesanan").child(cTanggal).child("Kamar").child("01").child(idpsn).setValue(pesan).addOnCompleteListener {
                                Toast.makeText(this@KeranjangActivity, "Berhasil, Pesanan akan segera diantar", Toast.LENGTH_SHORT).show()
                            }
                         }
                    listView.add(teman!!)
                }
                rv_View_keranjang.layoutManager = LinearLayoutManager(this@KeranjangActivity)
                rv_View_keranjang.adapter = KeranjangAdapter(this@KeranjangActivity,listView)
            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_keranjang, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.delete_keranjang -> {
                ref = FirebaseDatabase.getInstance().getReference()
                ref.child("Kamar").child("01").child("Keranjang").removeValue().addOnCompleteListener {
                    Toast.makeText(this@KeranjangActivity, "Data Terhapus", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
