package com.example.barcodehotel.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.barcodehotel.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_login.*

class AdminLogin : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        supportActionBar?.title = "Login Admin"

        mAuth = FirebaseAuth.getInstance()

        btn_masuk.setOnClickListener {
            loginEmailPassword()
        }


    }
    private fun loginEmailPassword() {
        val email = edt_email.text.toString()
        val password = edt_pass.text.toString()

        if (email == "") {
            Toast.makeText(this@AdminLogin, "Isi Email", Toast.LENGTH_LONG).show()
        }
        else if (password == "") {
            Toast.makeText(this@AdminLogin, "Isi Password", Toast.LENGTH_LONG).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@AdminLogin, ManageDataActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@AdminLogin, "email atau password salah", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

//    private fun moveNextActivity() {
//        var currentUser = FirebaseAuth.getInstance().currentUser
//        if(currentUser != null){
////            val intent = Intent(this@AdminLogin, AdminMainActivity::class.java)
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
////            startActivity(intent)
////            finish()
//            Toast.makeText(this@AdminLogin, "Login Sukses", Toast.LENGTH_LONG).show()
//        }
//    }

}
