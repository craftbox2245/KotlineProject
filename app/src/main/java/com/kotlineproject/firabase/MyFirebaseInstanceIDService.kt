package com.kotlineproject.firabase

import com.craftbox.hellokotlin.netUtils.MyPreferences
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by CRAFT BOX on 2/24/2018.
 */

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    internal var refreshedToken: String? = null
    lateinit var myPreferences: MyPreferences
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        try {
            myPreferences = MyPreferences(this@MyFirebaseInstanceIDService)
            val refreshedToken = FirebaseInstanceId.getInstance().token
            myPreferences.setPreferences(myPreferences.refreshedToken, refreshedToken!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
