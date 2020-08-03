package com.example.barcodehotel

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.barcodehotel.Model.ProfilModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_barcode.*
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.jar.Manifest

class ScanBarcodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false
    private lateinit var refAuth: FirebaseAuth
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_barcode)

        title = "Scan QR Code"

        refAuth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference()

        initScannerView()
    }

    private fun initScannerView(){
        mScannerView = object : ZXingScannerView(this){
            override fun createViewFinderView(context: Context?): IViewFinder {
                return CustomScanner(context!!)
            }
        }

        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_barcode.addView(mScannerView)
    }
    override fun onStart(){
        super.onStart()
        mScannerView.startCamera()
        doRequestPermission()
    }
    private fun doRequestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            100 -> {
                initScannerView()
            }
            else -> { }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }
    override fun handleResult(p0: Result?) {
        //@olino.garden = sub tambahan
        //QR code yg benar, example : 45@olino.garden (45 adalah no kamar)
        val empass = p0?.text.toString()
        val pisah = empass?.replace("@olino.garden","")

        val iduser = pisah
        if(pisah == empass){
            Toast.makeText(this@ScanBarcodeActivity, "Gagal membaca data, Hubungi CS", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@ScanBarcodeActivity, "Mohon Tunggu", Toast.LENGTH_LONG).show()
            refAuth.createUserWithEmailAndPassword(empass,empass)
                .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val intent = Intent(this@ScanBarcodeActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    val profile = ProfilModel(iduser,empass,empass)
                    ref.child("User").child(iduser).setValue(profile).addOnCompleteListener {}

                }
            }
        }
    }
}
