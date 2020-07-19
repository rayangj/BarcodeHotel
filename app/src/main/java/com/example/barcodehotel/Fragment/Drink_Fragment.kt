package com.example.barcodehotel.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.barcodehotel.R
import com.example.barcodehotel.Admin.Tambah_Makan
import kotlinx.android.synthetic.main.fragment_drink.*

/**
 * A simple [Fragment] subclass.
 */
class Drink_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tmb_mkn.setOnClickListener {
            //savedata()
            val intent = Intent(getActivity(), Tambah_Makan::class.java)
            getActivity()?.startActivity(intent)
        }
    }

}
