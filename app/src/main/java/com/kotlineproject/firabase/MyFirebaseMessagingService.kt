package com.kotlineproject.firabase

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.support.v4.app.NotificationManagerCompat
import android.util.Log

import com.craftbox.hellokotlin.netUtils.MyPreferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kotlineproject.MainActivity
import com.kotlineproject.R

import java.util.Random
import android.app.NotificationManager
import android.widget.RemoteViews
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.os.AsyncTask
import com.google.android.gms.internal.e
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


/**
 * Created by CRAFT BOX on 2/24/2018.
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {
    lateinit var myPreferences: MyPreferences

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // [START_EXCLUDE]

        Log.d(TAG, "From: " + remoteMessage!!.from)
        try {
            myPreferences = MyPreferences(this@MyFirebaseMessagingService)
            if (myPreferences.getPreferences(myPreferences.id) != "") {
                val id = myPreferences.getPreferences(myPreferences.id)
                val uid = remoteMessage.data["user_id"]
                print("" + id + "" + uid)
                if (myPreferences.getPreferences(myPreferences.id) == "" + remoteMessage.data["user_id"] || remoteMessage.data["user_id"] == "0") {
                    // var notification_title =""
                    var notification_title = remoteMessage.data["notification_title"]
                    val notification_description = remoteMessage.data["notification_description"]
                    val notification_type = remoteMessage.data["notification_type"]
                    if (notification_type.equals("1")) {
                        val r = Random()
                        val notificationId = r.nextInt(80 - 65) + 65
                        val viewIntent = Intent(this@MyFirebaseMessagingService, MainActivity::class.java)
                        viewIntent.putExtra("type", "1")
                        val viewPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, notificationId, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        val notif = Notification.Builder(this@MyFirebaseMessagingService)
                                .setContentTitle("" + notification_title)
                                .setContentText("" + notification_description)
                                .setContentIntent(viewPendingIntent)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setLargeIcon(largeIcon)
                                .setSound(defaultSoundUri)
                                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                                .setAutoCancel(true)
                                .build()
                        val notificationManager = NotificationManagerCompat.from(this@MyFirebaseMessagingService)
                        notificationManager.notify(notificationId, notif)
                    } else {
                        try {
                            val image_path = remoteMessage.data["image_path"]
                            if (!image_path.equals("")) {
                                Load(this@MyFirebaseMessagingService, "", notification_title!!, notification_description!!, image_path!!, "");
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    Log.e(">>>", "ok")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun Load(context: Context, type: String, title: String, description: String, imageUrl: String, image_path_inside: String) {
        generatePictureStyleNotification(context, type, title, description, imageUrl, image_path_inside).execute()
    }

    inner class generatePictureStyleNotification(private val mContext: Context, private val type: String, private var title: String, private var description: String, private val imageUrl: String, private var image_path_inside: String) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg params: String): Bitmap? {

            val `in`: InputStream
            try {
                val url = URL(this.imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.setDoInput(true)
                connection.connect()
                `in` = connection.getInputStream()
                return BitmapFactory.decodeStream(`in`)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onPostExecute(result: Bitmap) {
            super.onPostExecute(result)

            var intent1: Intent? = null
            val customNotifView = RemoteViews(applicationContext.packageName,
                    R.layout.customnotification_jumbo)
            intent1 = Intent(applicationContext, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent1.putExtra("type", "" + type)
            intent1.putExtra("title", "" + title)
            intent1.putExtra("description", "" + description)
            intent1.putExtra("image_path", "" + image_path_inside)

            val pIntent = PendingIntent.getActivity(applicationContext, 0, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            customNotifView.setTextViewText(R.id.txt_noti_header, "" + title)
            customNotifView.setTextViewText(R.id.txt_noti_desc, "" + description)
            customNotifView.setImageViewBitmap(R.id.img_noti_image, result)

            val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            customNotifView.setImageViewBitmap(R.id.imageView1, largeIcon)
            val notification = Notification.Builder(this@MyFirebaseMessagingService).setContentTitle("Notification title")
                    .setContentText("Notification content")
                    .setContent(customNotifView)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build()
            notification.bigContentView = customNotifView
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notification)
        }
    }

    companion object {
        private val TAG = "MyFirebaseMsgService"
    }
}
