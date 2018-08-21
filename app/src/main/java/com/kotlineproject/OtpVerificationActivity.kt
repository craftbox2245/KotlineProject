package com.kotlineproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import com.kotlineproject.netUtils.RetrofitClient

class OtpVerificationActivity : AppCompatActivity() {

    lateinit var receiver: BroadcastReceiver
    lateinit var otpEdt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        otpEdt = findViewById(R.id.otp_edt) as EditText

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        try {
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val b = intent.extras
                    val sender = b!!.getString("sender")
                    if (sender!!.indexOf("" + RetrofitClient.message_pack_name) > 0) {
                        val message = b.getString("message")
                        otpEdt.setText("" + message!!.replace("\\D+".toRegex(), ""))
                        abortBroadcast()
                    }
                }
            }
            val intentFilter = IntentFilter()
            intentFilter.priority = 1
            intentFilter.addAction("com.kotlineproject.reciver.onMessageReceived")
            registerReceiver(receiver, intentFilter)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}
