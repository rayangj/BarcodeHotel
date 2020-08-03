package com.example.barcodehotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var refAuth: FirebaseAuth
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        refAuth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()

        getNoKam()
//        if(refAuth.getCurrentUser() != null){
//            //ref = FirebaseDatabase.getInstance().getReference()
//
//        }
//        else{
//            val intent = Intent(this@SplashScreenActivity, ScanBarcodeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
    private fun getNoKam(){
        val currentUser = refAuth.currentUser
        val e = currentUser?.email.toString()
        val user2 = e.replace("@olino.garden","")

        ref.child("User").child(user2).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@SplashScreenActivity, "Database Erorr njir", Toast.LENGTH_SHORT).show()
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    val intent = Intent(this@SplashScreenActivity, ScanBarcodeActivity::class.java)
                    startActivity(intent)
                    finish()
                    currentUser?.delete()
                    refAuth.signOut()
                }
            }
        })
    }
}
