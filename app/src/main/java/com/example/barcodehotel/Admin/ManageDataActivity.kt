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
import com.example.barcodehotel.Admin.Fragment.ManageDrinkFragment
import com.example.barcodehotel.Admin.Fragment.ManageFoodFragment
import com.example.barcodehotel.R
import kotlinx.android.synthetic.main.activity_manage_data.*

class ManageDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_data)

        supportActionBar?.title = "Manage Data"
        supportActionBar?.elevation = 0.0f

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
            R.id.menu_tambahItem -> startActivity(Intent(this@ManageDataActivity, Tambah_Makan::class.java))
            R.id.menu_logOut -> startActivity(Intent(this@ManageDataActivity, AdminMainActivity::class.java))
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}
