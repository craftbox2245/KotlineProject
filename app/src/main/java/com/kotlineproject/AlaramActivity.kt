package com.kotlineproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.kotlineproject.broadcast.AlaramReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlaramActivity : AppCompatActivity() {

    lateinit var dateTime: TextView
    lateinit var title_edt: EditText
    lateinit var desc_edt: EditText
    lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alaram)

        dateTime = findViewById(R.id.date_time) as TextView
        title_edt = findViewById(R.id.title_edt) as EditText
        desc_edt = findViewById(R.id.desc_edt) as EditText
        submit = findViewById(R.id.submit) as Button

        val calNow = Calendar.getInstance()
        val cu_date = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val current_date1 = cu_date.format(calNow.time)
        dateTime.text = "" + current_date1

        submit.setOnClickListener {
            try {

                if (title_edt.text.equals("")) {
                    Toast.makeText(this@AlaramActivity, "Enter Title ", Toast.LENGTH_SHORT).show()
                } else if (desc_edt.text.equals("")) {
                    Toast.makeText(this@AlaramActivity, "Enter Description", Toast.LENGTH_SHORT).show()
                } else {
                    val current_date = dateTime.getText().toString().split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    val date = current_date[0].split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    val time = current_date[1].split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    val hour = Integer.parseInt("" + time[0])
                    val minute = Integer.parseInt("" + time[1])
                    val calNow = Calendar.getInstance()
                    calNow.set(Calendar.YEAR, Integer.parseInt("" + date[2]))
                    calNow.set(Calendar.MONTH, Integer.parseInt("" + date[1]) - 1)
                    calNow.set(Calendar.DAY_OF_MONTH, Integer.parseInt("" + date[0]))
                    calNow.set(Calendar.HOUR, hour)
                    calNow.set(Calendar.MINUTE, minute + 2)
                    if (current_date[2].toLowerCase() == "am") {
                        calNow.set(Calendar.AM_PM, Calendar.AM)
                    } else {
                        calNow.set(Calendar.AM_PM, Calendar.PM)
                    }
                    val r = Random()
                    val i1 = r.nextInt(200 - 28) + 28
                    setAlarm(calNow, i1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun setAlarm(targetCal: Calendar, reqCode: Int) {
        try {
            val intent = Intent(baseContext, AlaramReceiver::class.java)
            val bundle = Bundle()
            bundle.putString("title", "" + title_edt.text.toString())
            bundle.putString("desc", "" + desc_edt.text.toString())
            intent.putExtras(bundle)
            val pendingIntent = PendingIntent.getBroadcast(baseContext, reqCode, intent, 0)
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.timeInMillis, pendingIntent)
            title_edt.setText("")
            desc_edt.setText("")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

