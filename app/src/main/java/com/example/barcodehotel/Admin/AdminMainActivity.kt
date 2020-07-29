package com.example.barcodehotel.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.barcodehotel.Admin.Fragment.PesananBaruFragment
import com.example.barcodehotel.Admin.Fragment.SemuaPesananFragment
import com.example.barcodehotel.R
import kotlinx.android.synthetic.main.activity_admin_main.*

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        supportActionBar?.title = "Pesanan"
        supportActionBar?.elevation = 0.0f

        val adapter = TabAdapterAdmin(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPagerAdmin.adapter = adapter

        tabLayoutAdmin.setupWithViewPager(viewPagerAdmin)
    }
    class TabAdapterAdmin(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior){
        private val tabName : Array<String> = arrayOf("Menunggu", "Ditanggapi")

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
        when(item!!.itemId){
            R.id.menu_manageData -> startActivity(Intent(this@AdminMainActivity, AdminLogin::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}
