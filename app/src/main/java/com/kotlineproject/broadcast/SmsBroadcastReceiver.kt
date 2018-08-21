package com.kotlineproject.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

/**
 * Created by CRAFT BOX on 1/25/2017.
 */

class SmsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "OnReceive ++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        try {
            val bndl = intent.extras
            var msg: Array<SmsMessage?>
            var sender = ""
            var message = ""
            if (null != bndl) {
                //---retrieve the SMS message received---
                val pdus = bndl.get("pdus") as Array<Any>
                msg = arrayOfNulls(pdus.size)

                for (i in msg.indices) {
                    msg[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                    sender = msg[i]!!.originatingAddress
                    message = msg[i]!!.messageBody.toString()

                }
                //---display incoming SMS as a Android Toast---
                val intent1 = Intent()
                intent1.putExtra("sender", "" + sender)
                intent1.putExtra("message", "" + message)
                intent1.action = "com.kotlineproject.reciver.onMessageReceived"
                context.sendOrderedBroadcast(intent1, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "MyBroadCastReceiver"
    }
}
