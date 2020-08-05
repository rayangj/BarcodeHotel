package com.example.barcodehotel.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Admin.Adapter.PesananSemuaAdapter
import com.example.barcodehotel.Model.PesananModel
import com.example.barcodehotel.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show_pesanan.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShowPesananActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<PesananModel>
    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_pesanan)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        bundle = intent.extras
        val id_item = bundle!!.getCharSequence("id_item").toString()

        title = "Pesanan Kamar No $id_item"

        ref = FirebaseDatabase.getInstance().getReference()

        getData()
    }
    private fun getData(){
    Toast.makeText(this@ShowPesananActivity, "Mengambil Data...", Toast.LENGTH_SHORT).show()
    ref = FirebaseDatabase.getInstance().getReference()

    bundle = intent.extras
    val id_item = bundle!!.getCharSequence("id_item").toString()

    val tanggal = SimpleDateFormat("dd MMM yyyy")
    val cTanggal = tanggal.format(Date())

    ref.child("Pesanan").child(cTanggal).child("Pesan").orderByChild("nokamar").equalTo(id_item).addValueEventListener(object :
        ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            Toast.makeText(this@ShowPesananActivity, "Data tidak tampil", Toast.LENGTH_SHORT).show()
        }

        override fun onDataChange(p0: DataSnapshot) {
            listView= java.util.ArrayList<PesananModel>()

            for (dataSnapshot in p0.children ) {
                val teman = dataSnapshot.getValue(PesananModel::class.java)

                listView.add(teman!!)
            }
            rv_show_pesanan.layoutManager = LinearLayoutManager(this@ShowPesananActivity)
            rv_show_pesanan.adapter = PesananSemuaAdapter(this@ShowPesananActivity,listView)
        }

    })
}
}
