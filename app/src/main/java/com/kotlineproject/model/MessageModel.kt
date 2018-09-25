package com.kotlineproject.model

import android.graphics.Bitmap

import java.io.Serializable

/**
 * Created by Craftbox-4 on 12/15/2016.
 */

class MessageModel(var message_id: String, message: String, var sender_id: String,
                   var receiver_id: String, var flag: String, var time: String, var message_type: String, var attachmenr_url: String) : Serializable {
    var message = ""

    init {
        this.message = message
    }
}
