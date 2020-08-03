package com.example.barcodehotel.Admin.Adapter

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
import kotlinx.android.synthetic.main.pesanan_dikonfirmasi_items.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PesananSemuaAdapter (private val context: Context, private val list: ArrayList<PesananModel>)
    : RecyclerView.Adapter<PesananSemuaAdapter.ViewHolder>(){
    lateinit var ref : DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(context).inflate(R.layout.pesanan_dikonfirmasi_items, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))

        ref = FirebaseDatabase.getInstance().getReference()

        if(holder.txt_status.text.toString() == "0"){
            holder.containerView.visibility = View.GONE
        }

    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: PesananModel){
            show_no_kamar.text = list.nokamar
            show_tanggal.text = list.tanggal
            show_jam.text = list.jam
            total.text = "Rp." + list.finalTotal

            val st = list.status
            if(st == "1"){
                txt_status.text = "Ditolak"
                txt_status.setBackgroundColor(Color.parseColor("#BD1A1A"))
            }
            else if(st == "2"){
                txt_status.text = "Diterima"
                txt_status.setBackgroundColor(Color.parseColor("#1C9828"))
            }
            else {
                txt_status.text = "0"
                txt_status.setTextColor(Color.parseColor("#FFEB3B"))
            }

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
            harga.text = "Rp." + hargas
            jumlah.text = " x " + jumlahs

        }
    }
}