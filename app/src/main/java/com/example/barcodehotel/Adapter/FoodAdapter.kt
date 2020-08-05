package com.example.barcodehotel.Adapter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
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
import kotlinx.android.synthetic.main.food_items.*
import kotlinx.android.synthetic.main.food_items.gone_gambar
import kotlinx.android.synthetic.main.food_items.gone_id
import kotlinx.android.synthetic.main.food_items.view.*
import kotlinx.android.synthetic.main.keranjang_items.*
import kotlinx.android.synthetic.main.manage_item.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class FoodAdapter(private val context: Context, private val list: ArrayList<FoodModel>)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    lateinit var ref : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    lateinit var storageRef: StorageReference
    private var bundle: Bundle? = null

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
            holder.cv_item.setOnClickListener {
                val inflate_view = LayoutInflater.from(context).inflate(R.layout.activity_detail_item, null)
                val iv_item = inflate_view.findViewById(R.id.ImageItem) as ImageView
                val txt_nama_item = inflate_view.findViewById(R.id.NamaItem) as TextView
                val txt_harga_item = inflate_view.findViewById(R.id.HargaItem) as TextView
                val btn_tambahItem = inflate_view.findViewById(R.id.btn_tambahKeranjangItem) as Button
                val btn_keluar = inflate_view.findViewById(R.id.btn_close) as TextView

                mAuth = FirebaseAuth.getInstance()

                val currentUser = mAuth.currentUser
                val e = currentUser?.email.toString()
                val show = e.replace("@olino.garden","")


                val idmkn = holder.gone_id.text.toString()
                val namaItem = holder.show_nama.text.toString()
                txt_nama_item.setText(namaItem)
                val hargaItem = holder.show_harga.text.toString()
                txt_harga_item.setText(hargaItem)
                val gambarItem = holder.gone_gambar.text.toString()
                Glide.with(context)
                    .load(gambarItem)
                    .into(iv_item)

                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setView(inflate_view)
                alertDialog.setCancelable(true)


                val dialog = alertDialog.create()
                dialog.show()

                val user = KeranjangModel(idmkn,nama2,harga2,"1",harga2,gambar)
                btn_tambahItem.setOnClickListener {
                    ref.child("Kamar").child(show).child("Keranjang").child(idmkn).setValue(user).addOnCompleteListener {
                        Toast.makeText(context, "Ditambahkan ke Keranjang" , Toast.LENGTH_SHORT).show()
                    }
                    dialog.cancel()
                }

                btn_keluar.setOnClickListener {
                    dialog.cancel()
                }
            }
        }
        else{
            holder.btn_tambah_ke_keranjang.visibility = View.GONE
            holder.txt_habis.visibility = View.VISIBLE

            holder.cv_item.setOnClickListener {
                val inflate_view = LayoutInflater.from(context).inflate(R.layout.activity_detail_item, null)
                val iv_item = inflate_view.findViewById(R.id.ImageItem) as ImageView
                val txt_nama_item = inflate_view.findViewById(R.id.NamaItem) as TextView
                val txt_harga_item = inflate_view.findViewById(R.id.HargaItem) as TextView
                val btn_tambahItem = inflate_view.findViewById(R.id.btn_tambahKeranjangItem) as Button
                val btn_keluar = inflate_view.findViewById(R.id.btn_close) as TextView

                mAuth = FirebaseAuth.getInstance()

                val currentUser = mAuth.currentUser
                val e = currentUser?.email.toString()
                val show = e.replace("@olino.garden","")


                val idmkn = holder.gone_id.text.toString()
                val namaItem = holder.show_nama.text.toString()
                txt_nama_item.setText(namaItem)
                val hargaItem = holder.show_harga.text.toString()
                txt_harga_item.setText(hargaItem)
                val gambarItem = holder.gone_gambar.text.toString()
                Glide.with(context)
                    .load(gambarItem)
                    .into(iv_item)

                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setView(inflate_view)
                alertDialog.setCancelable(true)


                val dialog = alertDialog.create()
                dialog.show()

                btn_tambahItem.setBackgroundResource(R.drawable.btn_stock_gone)
                btn_tambahItem.setText("Habis")
                btn_tambahItem.setOnClickListener {
                    dialog.cancel()
                }

                btn_keluar.setOnClickListener {
                    dialog.cancel()
                }
            }
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