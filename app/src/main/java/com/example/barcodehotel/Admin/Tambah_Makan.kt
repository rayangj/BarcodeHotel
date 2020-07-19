package com.example.barcodehotel.Admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.barcodehotel.Model.FoodModel
import com.example.barcodehotel.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_tambah_makan.*

class Tambah_Makan : AppCompatActivity() {

    lateinit var kat: Spinner
    lateinit var storageRef: StorageReference
    lateinit var ref : DatabaseReference
    private var imgPath: Uri? = null
    private var PICK_IMAGE_REQUEST = 71

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_makan)

        kat = findViewById(R.id.kat)

        tersedia.isChecked = true

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


        val getKat: String = kat.selectedItem.toString()
        val getStok: Int = set_stok.checkedRadioButtonId
        val getNama: String = txt_makanan?.getText().toString()
        val getHarga: String = txt_harga?.getText().toString()
        ref = FirebaseDatabase.getInstance().getReference()
        val idmkn = ref.push().key.toString()
//
        val radio: RadioButton = findViewById(getStok)
        val stok = radio.text.toString()

        when{
            getNama.isEmpty() -> txt_makanan.error = "Wajib Isi Nama"
            getHarga.isEmpty() -> txt_harga.error = "Wajib Isi Harga"
            else -> {
                if(imgPath == null){
                    Toast.makeText(this@Tambah_Makan, "Wajib Pilih gambar", Toast.LENGTH_SHORT).show()
                }
                else{
                   Toast.makeText(this@Tambah_Makan, "Mengupload...", Toast.LENGTH_SHORT).show()
                    storageRef.putFile(imgPath!!).addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener {
                            val user = FoodModel(idmkn, getNama, getHarga, stok, getKat, it.toString())

                            ref.child(getKat).child(idmkn).setValue(user).addOnCompleteListener {
                                val Intent = Intent (this, ManageDataActivity::class.java)
                                startActivity(Intent)
                                finish()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@Tambah_Makan, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@Tambah_Makan, it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}