package com.example.barcodehotel.Admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.PesananAdapter
import com.example.barcodehotel.Admin.Adapter.PesananBaruAdapter
import com.example.barcodehotel.Model.PesananModel

import com.example.barcodehotel.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_pesanan_baru.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PesananBaruFragment : Fragment() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<PesananModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan_baru, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference()

        getData()
    }
    private fun getData(){
        Toast.makeText(context, "Mengambil Data...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()

        val tanggal = SimpleDateFormat("dd MMM yyyy")
        val cTanggal = tanggal.format(Date())

        ref.child("Pesanan").child(cTanggal).child("Pesan").orderByChild("status").equalTo("0").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "Data tidak tampil", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                listView= java.util.ArrayList<PesananModel>()

                for (dataSnapshot in p0.children ) {
                    val teman = dataSnapshot.getValue(PesananModel::class.java)

                    listView.add(teman!!)
                }
                rv_pesanan_baru.layoutManager = LinearLayoutManager(context)
                rv_pesanan_baru.adapter = PesananBaruAdapter(context!!,listView)
            }

        })
    }


}
