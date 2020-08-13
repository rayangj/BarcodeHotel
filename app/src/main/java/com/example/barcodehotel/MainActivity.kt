package com.example.barcodehotel

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barcodehotel.Adapter.KeranjangAdapter
import com.example.barcodehotel.Fragment.Drink_Fragment
import com.example.barcodehotel.Fragment.Food_Fragment
import com.example.barcodehotel.Model.KeranjangModel
import com.example.barcodehotel.Model.PesananModel
import com.example.barcodehotel.Model.ProfilModel
import com.example.barcodehotel.Receiver.MyAlarmReceiver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_keranjang.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var ref : DatabaseReference
    lateinit var listView: ArrayList<ProfilModel>
//    private val REQUEST_CODE = 100
//    private lateinit var alarmManager: AlarmManager
//    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val e = currentUser?.email.toString()
        val show = e.replace("@olino.garden","")

        supportActionBar?.title = "Kamar No $show"
        supportActionBar?.elevation = 0.0f

        autoLogout()

//        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, MyAlarmReceiver::class.java)
//        pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.set(Calendar.HOUR_OF_DAY, 15)
//        calendar.set(Calendar.MINUTE, 3)
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )

        btn_lihat_keranjng.setOnClickListener {
            val intent = Intent(this@MainActivity, KeranjangActivity::class.java)
            startActivity(intent)
        }
        val adapter = TabAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

    private fun autoLogout(){
        ref = FirebaseDatabase.getInstance().getReference()
        val currentUser = mAuth.currentUser
        val tanggal = SimpleDateFormat("dd MMM yyyy")
        val cTanggal = tanggal.format(Date())
        Toast.makeText(this@MainActivity, "halo" + cTanggal, Toast.LENGTH_SHORT).show()
        Log.d("tanggal", cTanggal)

            fun onDataChange(p0: DataSnapshot) {
                    val teman = p0.getValue(ProfilModel::class.java)
                    val tanggalLogin = teman?.tanggal.toString()
                    Log.d("dicho", tanggalLogin)
                    Toast.makeText(this@MainActivity, "halooooo" + cTanggal, Toast.LENGTH_SHORT).show()
                    if (tanggalLogin != cTanggal) {
                        currentUser?.delete()
                        ref.child("User").removeValue()
                        mAuth.signOut()
                        startActivity(Intent(this@MainActivity, ScanBarcodeActivity::class.java))
                        finish()

                    }
            }
//        if (cJam == "22:55:00"){
//            currentUser?.delete()
//            ref.child("User").removeValue()
//            mAuth.signOut()
//            startActivity(Intent(this@MainActivity, ScanBarcodeActivity::class.java))
//            finish()
//        }
    }

    class TabAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior){
        private val tabName : Array<String> = arrayOf("Food", "Drink")

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Food_Fragment()
            1 -> Drink_Fragment()
            else -> Food_Fragment()
        }

        override fun getCount(): Int = 2
        override fun getPageTitle(position: Int): CharSequence? = tabName.get(position)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.history -> {
                startActivity(Intent(this@MainActivity, HistoryPesananActivity::class.java))
                //finish()
            }
//            R.id.cara_pesan -> {
//                mAuth.signOut()
//                startActivity(Intent(this@MainActivity, ScanBarcodeActivity::class.java))
//                finish()
//            }
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        alarmManager.cancel(pendingIntent)
//    }

    
}







