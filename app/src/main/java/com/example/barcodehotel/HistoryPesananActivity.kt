package com.example.barcodehotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.PesananAdapter
import com.example.barcodehotel.Model.PesananModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_history_pesanan.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryPesananActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<PesananModel>
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_pesanan)

        title = "History Pesanan"

        getData()
    }
    private fun getData(){
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val e = currentUser?.email.toString()
        val show = e.replace("@olino.garden","")

        Toast.makeText(this, "Mengambil Data...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()

        val tanggal = SimpleDateFormat("dd MMM yyyy")
        val cTanggal = tanggal.format(Date())

        ref.child("Pesanan").child(cTanggal).child("Pesan").orderByChild("nokamar").equalTo(show).addValueEventListener(object :
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
