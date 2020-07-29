package com.example.barcodehotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.barcodehotel.Admin.AdminMainActivity
import com.example.barcodehotel.Fragment.Drink_Fragment
import com.example.barcodehotel.Fragment.Food_Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val e = currentUser?.email.toString()
        val show = e.replace("@olino.garden","")

        supportActionBar?.title = "Kamar No $show"
        supportActionBar?.elevation = 0.0f

        btn_lihat_keranjng.setOnClickListener {
            val intent = Intent(this@MainActivity, KeranjangActivity::class.java)
            startActivity(intent)
        }

        val adapter = TabAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
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
            R.id.cara_pesan -> {
//                mAuth.signOut()
//                startActivity(Intent(this@MainActivity, ScanBarcodeActivity::class.java))
//                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
