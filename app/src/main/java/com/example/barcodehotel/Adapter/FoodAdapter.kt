package com.example.barcodehotel.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.Model.KeranjangModel
import com.example.barcodehotel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.food_items.*
import kotlinx.android.synthetic.main.food_items.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class FoodAdapter(private val context: Context, private val list: ArrayList<FoodModel>)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    lateinit var ref : DatabaseReference
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.food_items, parent, false)

    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))
        ref = FirebaseDatabase.getInstance().getReference()

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val e = currentUser?.email.toString()
        val show = e.replace("@olino.garden","")

        val idmkn = holder.gone_id.text.toString()
        val nama2 = holder.show_nama.text.toString()
        val harga2 = holder.show_harga.text.toString()
        val gambar = holder.gone_gambar.text.toString()
        val stoks = holder.gone_stok.text.toString()

        if(stoks == "Tersedia"){
            val user = KeranjangModel(idmkn,nama2,harga2,"1",harga2,gambar)
            holder.btn_tambah_ke_keranjang.setOnClickListener {
                ref.child("Kamar").child(show).child("Keranjang").child(idmkn).setValue(user).addOnCompleteListener {
                    Toast.makeText(context, "Ditambahkan ke Keranjang" , Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            holder.btn_tambah_ke_keranjang.visibility = View.GONE
            holder.txt_habis.visibility = View.VISIBLE
        }

    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: FoodModel){
            show_harga.text = list.harga
            show_nama.text = list.nama
            gone_gambar.text = list.gambar
            gone_id.text = list.id
            gone_stok.text = list.stok
            Glide.with(itemView.context).load(list.gambar).into(itemView.show_gmb)


        }
    }


}