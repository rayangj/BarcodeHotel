package com.example.barcodehotel.Admin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodehotel.Model.PesananModel
import com.example.barcodehotel.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pesanan_baru_item.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PesananBaruAdapter (private val context: Context, private val list: ArrayList<PesananModel>)
    : RecyclerView.Adapter<PesananBaruAdapter.ViewHolder>(){
    lateinit var ref : DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(context).inflate(R.layout.pesanan_baru_item, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))

        ref = FirebaseDatabase.getInstance().getReference()

        val idpsn = holder.gone_idpsn.text.toString()
        val tanggal = SimpleDateFormat("dd MMM yyyy")
        val cTanggal = tanggal.format(Date())

        holder.btn_tolak.setOnClickListener{
            ref.child("Pesanan").child(cTanggal).child("Pesan").child(idpsn).child("status").setValue("1").addOnCompleteListener {  }
        }
        holder.btn_konfirm.setOnClickListener{
            ref.child("Pesanan").child(cTanggal).child("Pesan").child(idpsn).child("status").setValue("2").addOnCompleteListener {  }
        }
    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: PesananModel){
            gone_idpsn.text = list.id
            show_no_kamar.text = list.nokamar
            show_tanggal.text = list.tanggal
            show_jam.text = list.jam
            total.text = list.finalTotal

            val n = list.nama
            val h = list.total
            val j = list.jumlah

            val i = n.substring(0, n.length - 1)
            val p = h.substring(0, h.length - 1)
            val o = j.substring(0, j.length - 1)

            val namas = i.replace("-", "\n")
            val hargas = p.replace("-", "\n")
            val jumlahs = o.replace("-", "\n x ")

            nama.text = namas
            harga.text = hargas
            jumlah.text = " x " + jumlahs

        }
    }
}