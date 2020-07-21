package com.example.barcodehotel.Admin.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barcodehotel.Admin.EditItem
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.manage_item.*
import kotlinx.android.synthetic.main.manage_item.view.*

class AdminFoodAdapter (private val context: Context, private val list: ArrayList<FoodModel>)
    : RecyclerView.Adapter<AdminFoodAdapter.ViewHolder>(){

    lateinit var ref : DatabaseReference
    lateinit var storageRef: StorageReference
    lateinit var cs: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(context).inflate(R.layout.manage_item, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))

        holder.itemView.setOnLongClickListener(View.OnLongClickListener{view ->
            val cek_stok = holder.set_stok.getText().toString()
            val g_gambar = holder.gone_gambar.text.toString()
            val idmkn = holder.gone_id.text.toString()
            val kat = holder.gone_kat.text.toString()
            ref = FirebaseDatabase.getInstance().getReference()
            storageRef = FirebaseStorage.getInstance().getReference("Gambar")

            if(cek_stok == "Tersedia"){ cs = "Habis" }
            else{ cs = "Tersedia" }

            val action = arrayOf("Edit", "Delete", "Set $cs")
            val alert = AlertDialog.Builder(view.context)
            alert.setCancelable(true)

            alert.setItems(action){ dialog, which ->
                when(which){
                    0 ->{
                        val pindah = Intent(context, EditItem::class.java)

                        val bundle = Bundle()
                        bundle.putString("id_item", holder.gone_id.getText().toString())
                        bundle.putString("nama_item", holder.manage_nama.getText().toString())
                        bundle.putString("harga_item", holder.manage_harga.getText().toString())
                        bundle.putString("stok_item", holder.set_stok.getText().toString())
                        bundle.putString("kat_item", holder.gone_kat.getText().toString())
                        bundle.putString("gambar_item", holder.gone_gambar.getText().toString())

                        pindah.putExtras(bundle)
                        context.startActivity(pindah)
                    }
                    1 ->{
                        storageRef.child(idmkn).child(g_gambar).delete().addOnCompleteListener {
                            ref.child(kat).child(idmkn).removeValue().addOnCompleteListener {}
                        }
                    }
                    2 ->{
                         ref.child(kat).child(idmkn).child("stok").setValue(cs).addOnCompleteListener {}
                    }
                }
            }
            val dialog2 = alert.create()
            dialog2.show()
            true
        })
    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: FoodModel){
            manage_nama.text = list.nama
            manage_harga.text = list.harga
            gone_gambar.text = list.gambar
            gone_id.text = list.id
            gone_kat.text = list.kategori
            set_stok.text = list.stok
            val stok = list.stok
            Glide.with(itemView.context).load(list.gambar).into(itemView.show_gambar_manage)

            if(stok == "Tersedia"){
                set_stok.setBackgroundColor(Color.parseColor("#FF4BD649"))
            }
            else{
                set_stok.setBackgroundColor(Color.parseColor("#D64949"))
            }

        }
    }

}