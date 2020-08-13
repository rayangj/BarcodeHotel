package com.example.barcodehotel.Receiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.barcodehotel.KeranjangActivity

class MyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val intent = Intent(context, KeranjangActivity::class.java)
        context.startActivity(intent)
    }
}