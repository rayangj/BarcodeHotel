package com.example.barcodehotel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.barcodehotel.Model.FoodModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_tambah_makan.*
import java.io.IOException

class Tambah_Makan : AppCompatActivity() {

    lateinit var storageRef: StorageReference
    lateinit var ref : DatabaseReference
    private var Nama: EditText? = null
    private var Harga: EditText? = null
    private var imgPath: Uri? = null
    private var PICK_IMAGE_REQUEST = 71

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_makan)

        Nama = findViewById<EditText>(R.id.txt_makanan)
        Harga = findViewById<EditText>(R.id.txt_harga)
        ref = FirebaseDatabase.getInstance().getReference("Makanan")

        btn_tambah.setOnClickListener {
            savedata()
        }
        btn_upload.setOnClickListener{
            openGallery()
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

        val getNama: String = Nama?.getText().toString()
        val getHarga: String = Harga?.getText().toString()
        val idmkn = ref.push().key.toString()

        storageRef.putFile(imgPath!!).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                val user = FoodModel(idmkn,getNama,getHarga,it.toString())

                ref.child(idmkn).setValue(user).addOnCompleteListener {
                    Toast.makeText(this@Tambah_Makan, "Successs",Toast.LENGTH_SHORT).show()
                }
            }
        }
            .addOnFailureListener{
                Toast.makeText(this@Tambah_Makan, it.message,Toast.LENGTH_SHORT).show()
            }
        val Intent = Intent (this, MainActivity::class.java)
        startActivity(Intent)
        finish()
    }
}
