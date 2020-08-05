package com.example.barcodehotel.Admin

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.barcodehotel.Admin.Fragment.PesananBaruFragment
import com.example.barcodehotel.Admin.Fragment.SemuaPesananFragment
import com.example.barcodehotel.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_login.*
import kotlinx.android.synthetic.main.activity_admin_main.*

class AdminMainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        supportActionBar?.title = "Pesanan"
        supportActionBar?.elevation = 0.0f

        mAuth = FirebaseAuth.getInstance()

        val adapter = TabAdapterAdmin(
            supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPagerAdmin.adapter = adapter

        tabLayoutAdmin.setupWithViewPager(viewPagerAdmin)
    }

    class TabAdapterAdmin(fm: FragmentManager, behavior: Int) :
        FragmentStatePagerAdapter(fm, behavior) {
        private val tabName: Array<String> = arrayOf("Menunggu", "Ditanggapi")

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> PesananBaruFragment()
            1 -> SemuaPesananFragment()
            else -> PesananBaruFragment()
        }

        override fun getCount(): Int = 2
        override fun getPageTitle(position: Int): CharSequence? = tabName.get(position)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.menu_manageData -> {
//                startActivity(Intent(this@AdminMainActivity, AdminLogin::class.java))
                val inflate_view = LayoutInflater.from(this).inflate(R.layout.activity_admin_login, null)
                val btn_login = inflate_view.findViewById(R.id.btn_masuk) as Button
                val btn_keluar = inflate_view.findViewById(R.id.btn_close) as TextView
                val txt_email = inflate_view.findViewById(R.id.edt_email) as EditText
                val txt_pass = inflate_view.findViewById(R.id.edt_pass) as EditText
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setView(inflate_view)
                alertDialog.setCancelable(true)

                val dialog = alertDialog.create()
                dialog.show()

                btn_login.setOnClickListener {
                    val email = txt_email.text.toString()
                    val password = txt_pass.text.toString()

                    if (email == "") {
                        Toast.makeText(this@AdminMainActivity, "Isi Email", Toast.LENGTH_LONG)
                            .show()
                    } else if (password == "") {
                        Toast.makeText(this@AdminMainActivity, "Isi Password", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(
                                        this@AdminMainActivity,
                                        ManageDataActivity::class.java
                                    )
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@AdminMainActivity,
                                        "email atau password salah",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                }

                btn_keluar.setOnClickListener {
                    dialog.cancel()
                }
            }
        }

            return super.onOptionsItemSelected(item)

    }

//    private fun loginEmailPassword()  {
//        val email = edt_email.text.toString()
//        val password = edt_pass.text.toString()
//
//        if (email == "") {
//            Toast.makeText(this@AdminMainActivity, "Isi Email", Toast.LENGTH_LONG)
//                .show()
//        } else if (password == "") {
//            Toast.makeText(this@AdminMainActivity, "Isi Password", Toast.LENGTH_LONG)
//                .show()
//        } else {
//            mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val intent = Intent(
//                            this@AdminMainActivity,
//                            ManageDataActivity::class.java
//                        )
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        Toast.makeText(
//                            this@AdminMainActivity,
//                            "email atau password salah",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//        }
//    }
//    private fun keluar()  {
//        View.OnClickListener { this@AdminMainActivity.finish() }
//    }
}
