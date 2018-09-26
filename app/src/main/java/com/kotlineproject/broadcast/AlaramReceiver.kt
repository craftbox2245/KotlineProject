package com.kotlineproject.broadcast

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast

import com.kotlineproject.MainActivity
import com.kotlineproject.R

import java.util.Random

class AlaramReceiver : BroadcastReceiver() {

    override fun onReceive(arg0: Context, arg1: Intent) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show()

        try {

            val bundle = arg1.extras
            val title = bundle!!.getString("title")
            val desc = bundle.getString("desc")

            val r = Random()
            val notificationId = r.nextInt(80 - 65) + 65
            val viewIntent = Intent(arg0, MainActivity::class.java)
            val viewPendingIntent = PendingIntent.getActivity(arg0, notificationId, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val largeIcon = BitmapFactory.decodeResource(arg0.resources, R.mipmap.ic_launcher)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notif = Notification.Builder(arg0)
                    .setContentTitle("" + title!!)
                    .setContentText("" + desc!!)
                    .setContentIntent(viewPendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(largeIcon)
                    .setSound(defaultSoundUri)
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                    .setAutoCancel(true)
                    .build()
            val notificationManager = NotificationManagerCompat.from(arg0)
            notificationManager.notify(notificationId, notif)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
