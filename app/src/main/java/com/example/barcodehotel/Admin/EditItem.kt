package com.example.barcodehotel.Admin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_item.*
import java.text.NumberFormat
import java.util.*

class EditItem : AppCompatActivity() {

    lateinit var storageRef: StorageReference
    lateinit var ref : DatabaseReference
    private var imgPath: Uri? = null
    private var PICK_IMAGE_REQUEST = 71
    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference()

        bundle = intent.extras
        title = "Edit Item"
        val upload_gambar = findViewById<Button>(R.id.btn_upload) as Button
        val btn_edit_item = findViewById<Button>(R.id.btn_edit_item) as Button
        val nama = findViewById<EditText>(R.id.txt_makanan) as EditText
        val harga = findViewById<EditText>(R.id.txt_harga) as EditText
        val gambar = findViewById<ImageView>(R.id.image_view) as ImageView
        val setStok = bundle!!.getCharSequence("stok_item")

        gambar.setOnClickListener {
            Toast.makeText(this, setStok, Toast.LENGTH_SHORT).show()
        }
        if(setStok == "Tersedia"){
            tersedia.isChecked = true
        }
        else if(setStok == "Habis"){
            habis.isChecked = true
        }
        val harga2 = bundle!!.getCharSequence("harga_item")
        val manage_harga = harga2.toString().replace("Rp", "")
        val manage_harga2 = manage_harga.replace(".","")

        nama.setText(bundle!!.getCharSequence("nama_item"))
        harga.setText(manage_harga2)
        Glide.with(this).load(bundle!!.getCharSequence("gambar_item")).into(gambar)


        upload_gambar.setOnClickListener{
           openGallery()
        }
        btn_edit_item.setOnClickListener {
            savedata()
        }
    }
    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            imgPath = data?.data
            image_view.setImageURI(imgPath)

        }
    }
    private fun savedata() {
    storageRef = FirebaseStorage.getInstance().getReference("Gambar")

    bundle = intent.extras
    val getNama: String = txt_makanan?.getText().toString()
    val getHarga: String = txt_harga?.getText().toString()

    val localeID = Locale("in","ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    val hargamkn = numberFormat.format(getHarga.toDouble()).toString()

    val getKat = bundle!!.getCharSequence("kat_item").toString()
    val idmkn = bundle!!.getCharSequence("id_item").toString()
    val gmb_old = bundle!!.getCharSequence("gambar_item").toString()
    val getStok: Int = set_stok.checkedRadioButtonId


    val radio: RadioButton = findViewById(getStok)
    val stok = radio.text.toString()

        when{
            getNama.isEmpty() -> txt_makanan.error = "Wajib Isi Nama"
            getHarga.isEmpty() -> txt_harga.error = "Wajib Isi Harga"
            else -> {
                if (imgPath == null) {
                    val user = FoodModel(idmkn, getNama, hargamkn, stok, getKat, gmb_old)
                    Toast.makeText(this@EditItem, "Mengupload...", Toast.LENGTH_LONG).show()
                    ref.child(getKat).child(idmkn).setValue(user).addOnCompleteListener {
                        finish()
                    }
                }
                else{
                    finish()
                    Toast.makeText(this@EditItem, "Mengupload...", Toast.LENGTH_LONG).show()
                    storageRef.child(idmkn).putFile(imgPath!!).addOnSuccessListener {
                        storageRef.child(idmkn).downloadUrl.addOnSuccessListener {
                            val user = FoodModel(idmkn, getNama, hargamkn, stok, getKat, it.toString())
                            ref.child(getKat).child(idmkn).setValue(user).addOnCompleteListener {
                                val g_gambar = bundle!!.getCharSequence("gambar_item").toString()
                                storageRef = FirebaseStorage.getInstance().getReference("Gambar")
                                storageRef.child(g_gambar).delete().addOnCompleteListener {}
                             }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@EditItem, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}



