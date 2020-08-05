package com.example.barcodehotel.Adapter

import android.app.AlertDialog
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.Model.KeranjangModel
import com.example.barcodehotel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.keranjang_items.*
import kotlinx.android.synthetic.main.keranjang_items.view.*
import java.lang.Double
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class KeranjangAdapter(private val context: Context, private val list: ArrayList<KeranjangModel>)
    : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>(){

    lateinit var edt_jumlah : EditText
    lateinit var ref : DatabaseReference
    lateinit var storageRef: StorageReference
    lateinit var mAuth: FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.keranjang_items, parent, false)

    )
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))

        ref = FirebaseDatabase.getInstance().getReference()

        holder.btn_edit.setOnClickListener {
            val inflate_view = LayoutInflater.from(context).inflate(R.layout.activity_admin_login, null)
            val edt_jumlah = inflate_view.findViewById<EditText>(R.id.txt_ji)
            val btn_edit_jumlah = inflate_view.findViewById(R.id.btn_edit_jumlah_item) as Button
            val btn_hapus = inflate_view.findViewById(R.id.btn_hapus_item) as Button

            mAuth = FirebaseAuth.getInstance()
            val currentUser = mAuth.currentUser
            val e = currentUser?.email.toString()
            val show = e.replace("@olino.garden","")

            val old_jumlah = holder.jumlah_item.text.toString()
            edt_jumlah.setText(old_jumlah)

            val idmkn = holder.gone_id.text.toString()
            val title_bjor = holder.keranjang_nama.text.toString()

            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle(title_bjor)
            alertDialog.setView(inflate_view)
            alertDialog.setCancelable(true)


            val dialog = alertDialog.create()
            dialog.show()

            btn_edit_jumlah.setOnClickListener {
                val new_jumlah = edt_jumlah.text.toString()
                val harga1 = holder.gone_harga.text.toString()
                val hargas1 = harga1.replace("Rp", "")
                val hargas2 = hargas1.replace(".","")

                if(new_jumlah == ""){
                    Toast.makeText(context, "kolom tidak boleh kosong" , Toast.LENGTH_SHORT).show()
                }
                else if(new_jumlah == "0"){
                    Toast.makeText(context, "isi item minimal 1" , Toast.LENGTH_SHORT).show()

                }
                else{
                    val total = hargas2.toBigDecimal()
                    val jml = new_jumlah.toBigDecimal()
                    val total1 = total * jml
                    val finalTotal = total1.toString()

                    val localeID = Locale("in","ID")
                    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
                    val tootall = numberFormat.format(finalTotal.toDouble()).toString()
                    
                    ref.child("Kamar").child(show).child("Keranjang").child(idmkn).child("jumlah").setValue(new_jumlah).addOnCompleteListener {}
                    ref.child("Kamar").child(show).child("Keranjang").child(idmkn).child("total").setValue(tootall).addOnCompleteListener {}
                    dialog.cancel()
                }
            }
            btn_hapus.setOnClickListener {
                ref.child("Kamar").child(show).child("Keranjang").child(idmkn).removeValue().addOnCompleteListener {}
                dialog.cancel()
                Toast.makeText(context, "dihapus dari keranjang" , Toast.LENGTH_SHORT).show()
            }
        }
    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: KeranjangModel){
            keranjang_nama.text = list.nama
            keranjang_total.text = list.total
            gone_harga.text = list.harga
            jumlah_item.text = list.jumlah
            gone_gambar.text = list.gambar
            gone_id.text = list.id
            Glide.with(itemView.context).load(list.gambar).into(itemView.show_gambar)


        }
    }



}
