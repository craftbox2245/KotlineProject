package com.kotlineproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.craftbox.hellokotlin.netUtils.MyPreferences
import com.kotlineproject.netUtils.DBHelper
import com.kotlineproject.netUtils.RuntimePermissionsActivity

class SplashActivity : RuntimePermissionsActivity() {

    lateinit var myPreferences: MyPreferences
    lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        myPreferences = MyPreferences(this)
        db = DBHelper(this)

        Handler().postDelayed({
            super@SplashActivity.requestAppPermissions(arrayOf(android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    R.string.runtime_permissions_txt, 20)
        }, 500)


    }

    override fun onPermissionsGranted(requestCode: Int) {
        try {

            try {
                db.createDataBase()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (GlobalElements.isConnectingToInternet(this@SplashActivity)) {
                    if (myPreferences.getPreferences("" + myPreferences.id).equals("")) {
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    GlobalElements.showDialog(this@SplashActivity)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
