package com.example.barcodehotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.PesananAdapter
import com.example.barcodehotel.Model.PesananModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_history_pesanan.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryPesananActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<PesananModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_pesanan)

        title = "History Pesanan"

        getData()
    }
    private fun getData(){
        Toast.makeText(this, "Mengambil Data...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()

        val tanggal = SimpleDateFormat("EEE, dd MMM yyyy")
        val cTanggal = tanggal.format(Date())

        ref.child("Pesanan").child(cTanggal).child("Kamar").child("01").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@HistoryPesananActivity, "Data tidak tampil", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                listView= java.util.ArrayList<PesananModel>()

                for (dataSnapshot in p0.children ) {
                    val teman = dataSnapshot.getValue(PesananModel::class.java)

                    listView.add(teman!!)
                }
                rv_History_pesanan.layoutManager = LinearLayoutManager(this@HistoryPesananActivity)
                rv_History_pesanan.adapter = PesananAdapter(this@HistoryPesananActivity,listView)
            }

        })
    }
}
