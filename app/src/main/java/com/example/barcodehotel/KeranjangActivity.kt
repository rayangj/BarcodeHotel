package com.example.barcodehotel

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.KeranjangAdapter
import com.example.barcodehotel.Model.KeranjangModel
import com.example.barcodehotel.Model.PesananModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_keranjang.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class KeranjangActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<KeranjangModel>
    lateinit var mAuth: FirebaseAuth
    lateinit var show: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)
        title = "Keranjang"

        getData()
    }
    private fun getData(){
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val e = currentUser?.email.toString()
        show = e.replace("@olino.garden","")

        Toast.makeText(this, "Mengambil Data...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()

        ref.child("Kamar").child(show).child("Keranjang").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@KeranjangActivity, "Data tidak tampil", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                listView = java.util.ArrayList<KeranjangModel>()
                    var subTotal = 0
                    val idpsn = ref.push().key.toString()
                    var nama = ""
                    var harga = ""
                    var jumlah = ""
                    var total = ""
                    val tanggal = SimpleDateFormat("dd MMM yyyy")
                    val cTanggal = tanggal.format(Date())
                    val jam = SimpleDateFormat("HH:mm")
                    val cJam = jam.format(Date())

                    for (dataSnapshot in p0.children) {
                        val teman = dataSnapshot.getValue(KeranjangModel::class.java)
                        val totalItem = teman?.total!!.toString()
                        val jmlitem = totalItem.replace("Rp","")
                        val jmlitem2 = jmlitem.replace(".","")

                        val jml = jmlitem2.toInt()
                        subTotal = subTotal + jml

                        val name = teman.nama
                        val harge = teman.harga
                        val jumleh = teman.jumlah
                        val totel = teman.total
//
//
                        val localeID = Locale("in","ID")
                        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                        val hargamkn = numberFormat.format(subTotal.toDouble()).toString()

                        btn_pesan.text = hargamkn + " - Pesan"
//
                        nama = "$nama$name-"
                        harga = "$harga$harge-"
                        jumlah = "$jumlah$jumleh-"
                        total = "$total$totel-"

//
                        // status 0 = menuggu konfirmasi
                        // status 1 = ditolak
                        // status 2 = diterima
//
                        btn_pesan.setOnClickListener {
                            val alert = AlertDialog.Builder(this@KeranjangActivity)
                            alert.setTitle("Konfirmasi Pesanan")
                            alert.setMessage("Pesanan yang sudah diterima tidak bisa dibatalkan")
                            alert.setCancelable(false)
                            alert.setPositiveButton("YA"){_,_->
                                val pesan = PesananModel(idpsn, nama, show, "0", jumlah, total, hargamkn, cTanggal, cJam)
                                ref.child("Pesanan").child(cTanggal).child("Pesan").child(idpsn)
                                    .setValue(pesan).addOnCompleteListener {
                                        val intent = Intent(
                                            this@KeranjangActivity,
                                            BerhasilPesanActivity::class.java
                                        )
                                        startActivity(intent)
                                        finish()
                                    }
                            }
                            alert.setNegativeButton("BATAL"){_,_-> }
                            val mdialog = alert.create()
                            mdialog.show()


                        }
                        listView.add(teman!!)
                    }
                    rv_View_keranjang.layoutManager = LinearLayoutManager(this@KeranjangActivity)
                    rv_View_keranjang.adapter = KeranjangAdapter(this@KeranjangActivity, listView)
                }
                else{
                    Toast.makeText(this@KeranjangActivity, "Data Kosong", Toast.LENGTH_SHORT).show()
                    rv_View_keranjang.visibility = View.GONE
                    btn_pesan.visibility = View.GONE

                    krjng_kososng.visibility = View.VISIBLE
                    txt_krjng_kosong.visibility = View.VISIBLE

                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_keranjang, menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.delete_keranjang -> {
                val alert = AlertDialog.Builder(this@KeranjangActivity)
                alert.setTitle("Hapus Data")
                alert.setMessage("Apakah anda yakin ingin menghapus pesanan dari keranjang?")
                alert.setCancelable(false)
                alert.setPositiveButton("YA"){_,_->
                    ref = FirebaseDatabase.getInstance().getReference()
                    ref.child("Kamar").child(show).child("Keranjang").removeValue().addOnCompleteListener {
                        Toast.makeText(this@KeranjangActivity, "Keranjang Kosong", Toast.LENGTH_SHORT).show()
                        }
                }
                alert.setNegativeButton("BATAL"){_,_-> }
                val mdialog = alert.create()
                mdialog.show()

                }
            }
        return super.onOptionsItemSelected(item)
    }
}
