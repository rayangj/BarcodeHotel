package com.example.barcodehotel.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.Model.KeranjangModel
import com.example.barcodehotel.R
import com.example.barcodehotel.Tambah_Makan
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.food_items.*
import kotlinx.android.synthetic.main.food_items.view.*


class FoodAdapter(private val context: Context, private val list: ArrayList<FoodModel>)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    lateinit var ref : DatabaseReference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.food_items, parent, false)

    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))
        ref = FirebaseDatabase.getInstance().getReference()

        //val key = ref.child("Makanan").push().getKey()
        //val idmkn = ref.push().key.toString()
        val idmkn = holder.gone_id.text.toString()
        val nama2 = holder.show_nama.text.toString()
        val harga2 = holder.show_harga.text.toString()
        val gambar = holder.gone_gambar.text.toString()

        val user = KeranjangModel(idmkn,nama2,harga2,"1",gambar)

       holder.btn_tambah_ke_keranjang.setOnClickListener {
            ref.child("Kamar").child("01").child("Keranjang").child(idmkn).setValue(user).addOnCompleteListener {
                Toast.makeText(context, "Ditambahkan ke Keranjang" , Toast.LENGTH_SHORT).show()
            }
            //holder.btn_tambah_ke_keranjang.isEnabled = false
            //Toast.makeText(context, " $key" , Toast.LENGTH_SHORT).show()
        }
//        holder.containerView.setOnClickListener {
//            val keyi = holder.key
//        }
    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: FoodModel){
            show_nama.text = list.nama
            show_harga.text = list.harga
            gone_gambar.text = list.gambar
            gone_id.text = list.id
            Glide.with(itemView.context).load(list.gambar).into(itemView.show_gmb)


        }
    }


}