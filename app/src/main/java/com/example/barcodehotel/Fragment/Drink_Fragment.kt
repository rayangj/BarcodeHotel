package com.example.barcodehotel.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodehotel.Adapter.FoodAdapter

import com.example.barcodehotel.R
import com.example.barcodehotel.Admin.Tambah_Makan
import com.example.barcodehotel.KeranjangActivity
import com.example.barcodehotel.Model.FoodModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_drink.*

/**
 * A simple [Fragment] subclass.
 */
class Drink_Fragment : Fragment() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<FoodModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }
    private fun getData(){
        ref = FirebaseDatabase.getInstance().getReference("Minuman")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(getContext(), "Database Erorr njir", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                    listView = java.util.ArrayList<FoodModel>()
                    for (dataSnapshot in p0.children) {
                        val teman = dataSnapshot.getValue(FoodModel::class.java)
                        listView.add(teman!!)
                    }
                    rv_View.layoutManager = LinearLayoutManager(context)
                    rv_View.adapter = FoodAdapter(context!!, listView)
               }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
