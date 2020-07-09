package com.example.barcodehotel.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.food_items.*


class FoodAdapter(private val context: Context, private val list: ArrayList<FoodModel>)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.food_items, parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(position))
    }
    class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(list: FoodModel){
            show_nama.text = list.nama
            show_harga.text = list.harga
        }
    }

}