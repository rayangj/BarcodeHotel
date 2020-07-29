package com.example.barcodehotel.Admin.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodehotel.Admin.ShowPesananActivity
import com.example.barcodehotel.Model.ProfilModel
import com.example.barcodehotel.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.kamar_items.*

class NoKamarAdapter(private val context: Context, private val list: ArrayList<ProfilModel>)
    : RecyclerView.Adapter<NoKamarAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(context).inflate(R.layout.kamar_items, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))

        holder.containerView.setOnClickListener {
            val pindah = Intent(context, ShowPesananActivity::class.java)

            val bundle = Bundle()
            bundle.putString("id_item", holder.txt_kam.getText().toString())

            pindah.putExtras(bundle)
            context.startActivity(pindah)
        }
    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: ProfilModel){
            val e = list.email
            val show = e.replace("@olino.garden","")

            txt_kam.text = show
        }
    }
}