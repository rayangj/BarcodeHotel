package com.example.barcodehotel.Admin

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.barcodehotel.Admin.Fragment.ManageDrinkFragment
import com.example.barcodehotel.Admin.Fragment.ManageFoodFragment
import com.example.barcodehotel.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_manage_data.*
import java.text.SimpleDateFormat
import java.util.*

class ManageDataActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_data)

        supportActionBar?.title = "Manage Data"
        supportActionBar?.elevation = 0.0f

        mAuth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()

        val adapter = TabAdapterManage(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPagerManage.adapter = adapter

        tabLayoutManage.setupWithViewPager(viewPagerManage)
    }
    class TabAdapterManage(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior){
        private val tabName : Array<String> = arrayOf("Food", "Drink")

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> ManageFoodFragment()
            1 -> ManageDrinkFragment()
            else -> ManageFoodFragment()
        }

        override fun getCount(): Int = 2
        override fun getPageTitle(position: Int): CharSequence? = tabName.get(position)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tambah, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.menu_tambahItem -> {
                gotoTamahItem()
            }
            R.id.menu_hapusDataUser ->{
                val alert = AlertDialog.Builder(this@ManageDataActivity)
                alert.setTitle("Data yang dihapus adalah : ")
                alert.setMessage("\n- Data user login\n- Data pesanan user\n- Data keranjang user \n\nPastkan data sudah direkap sebelumnya")
                alert.setCancelable(false)
                alert.setPositiveButton("HAPUS"){_,_->
                    val tanggal = SimpleDateFormat("dd MMM yyyy")
                    val cTanggal = tanggal.format(Date())

                    ref.child("Kamar").removeValue()
                    ref.child("Pesanan").child(cTanggal).removeValue()
                    ref.child("User").removeValue()

                    Toast.makeText(this@ManageDataActivity, "Data Terhapus", Toast.LENGTH_SHORT).show()
                }
                alert.setNegativeButton("BATAL"){_,_-> }
                val mdialog = alert.create()
                mdialog.show()
            }
            R.id.menu_logOut -> {

                mAuth.signOut()
                startActivity(Intent(this@ManageDataActivity, AdminMainActivity::class.java))
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mAuth.signOut()
        startActivity(Intent(this@ManageDataActivity, AdminMainActivity::class.java))
        finish()
    }
     private fun gotoTamahItem(){
        val intent = Intent (this, Tambah_Makan::class.java)
        startActivity(intent)
    }
}
