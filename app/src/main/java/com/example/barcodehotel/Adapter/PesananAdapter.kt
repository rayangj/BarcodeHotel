package com.example.barcodehotel.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodehotel.Model.PesananModel
import com.example.barcodehotel.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pesanan_items.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PesananAdapter (private val context: Context, private val list: ArrayList<PesananModel>)
    : RecyclerView.Adapter<PesananAdapter.ViewHolder>(){

    lateinit var ref: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(context).inflate(R.layout.pesanan_items, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))
        ref = FirebaseDatabase.getInstance().getReference()
        val tanggal = SimpleDateFormat("dd MMM yyyy")
        val cTanggal = tanggal.format(Date())
        val sta = holder.show_status.text.toString()

        if(sta == "Menunggu Konfirmasi"){
            holder.btn_batal_psn.visibility = View.VISIBLE
            holder.btn_batal_psn.setOnClickListener {
                ref.child("Pesanan").child(cTanggal).child("Pesan").child(holder.gone_id.text.toString()).removeValue()
            }
        }

    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: PesananModel){
            gone_id.text = list.id
            val st = list.status
            if(st == "0"){
                show_status.text = "Menunggu Konfirmasi"
                show_status.setTextColor(Color.parseColor("#BDAE25"))
            }
            else if(st == "1"){
                show_status.text = "Ditolak"
                show_status.setTextColor(Color.parseColor("#BD1A1A"))
            }
            else if(st == "2"){
                show_status.text = "Diterima"
                show_status.setTextColor(Color.parseColor("#1C9828"))
            }


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
            val hargas = p.replace("-","\n")
            val jumlahs = o.replace("-", "\n x ")


            nama.text = namas
            harga.text = hargas
            jumlah.text = " x " + jumlahs

        }
    }
}