package com.craftbox.hellokotlin.netUtils
import android.content.Context
import com.kotlineproject.netUtils.AESCrypt

class MyPreferences {

    var context: Context
    var PreferenceName = "first"
    var EncraptionKey = "first@2442"

    var id = "u_id"
    var  name="name"
    var  password="password"
    var refreshedToken="refreshedToken"

    constructor(context: Context)
    {
        this.context = context;
    }

    internal var value = ""
    fun getPreferences(key: String): String {
        value = ""
        try {
            val channel = context.getSharedPreferences("" + PreferenceName, Context.MODE_PRIVATE)
            value = AESCrypt.decrypt("" + EncraptionKey, channel.getString("" + key, "")!!.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            value = ""
            return value
        }
        return value
    }

    fun setPreferences(key: String, value: String) {
        try {
            val sharedpreferences = context.getSharedPreferences("" + PreferenceName, Context.MODE_PRIVATE)
            val ed = sharedpreferences.edit()
            ed.putString("" + key, AESCrypt.encrypt("" + EncraptionKey, "" + value))
            ed.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearPreferences(): Boolean {
        try {
            val settings = context.getSharedPreferences("" + PreferenceName, Context.MODE_PRIVATE)
            return settings.edit().clear().commit()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}