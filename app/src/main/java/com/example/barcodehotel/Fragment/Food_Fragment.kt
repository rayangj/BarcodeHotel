package com.example.barcodehotel.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.FoodAdapter
import com.example.barcodehotel.Model.FoodModel

import com.example.barcodehotel.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_food.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class Food_Fragment : Fragment() {

    lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<FoodModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getData()
    }

    private fun getData(){
       Toast.makeText(getContext(), "Mohon tunggu sebentar...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference()
        ref.child("Makanan").addValueEventListener(object : ValueEventListener{
           override fun onCancelled(p0: DatabaseError) {
               Toast.makeText(getContext(), "Database Erorr njir", Toast.LENGTH_SHORT).show()
           }

           override fun onDataChange(p0: DataSnapshot) {
               listView= java.util.ArrayList<FoodModel>()
               for (dataSnapshot in p0.children ) {
                   val teman = dataSnapshot.getValue(FoodModel::class.java)
                   //teman?.setKey(dataSnapshot.key)
                   listView.add(teman!!)
               }
               rv_View.layoutManager = LinearLayoutManager(context)
               rv_View.adapter = FoodAdapter(context!!,listView)
               Toast.makeText(getContext(), "Data Berhasil Dimuat",Toast.LENGTH_LONG).show()
           }

       })
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
