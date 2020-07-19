package com.example.barcodehotel.Admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Admin.Adapter.AdminFoodAdapter
import com.example.barcodehotel.Model.FoodModel

import com.example.barcodehotel.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_manage_drink.*

/**
 * A simple [Fragment] subclass.
 */
class ManageDrinkFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_manage_drink, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }
    private fun getData(){
        Toast.makeText(getContext(), "Mengambil data...", Toast.LENGTH_SHORT).show()
        ref = FirebaseDatabase.getInstance().getReference("Minuman")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(getContext(), "Database Erorr njir", Toast.LENGTH_SHORT).show()
            }
            override fun onDataChange(p0: DataSnapshot) {

                listView= java.util.ArrayList<FoodModel>()
                for (dataSnapshot in p0.children ) {
                    val teman = dataSnapshot.getValue(FoodModel::class.java)
                    listView.add(teman!!)
                }
                rv_Drink_manage.layoutManager = LinearLayoutManager(context)
                rv_Drink_manage.adapter = AdminFoodAdapter(context!!,listView)
            }

        })
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}
