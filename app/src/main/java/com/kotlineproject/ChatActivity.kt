package com.kotlineproject

import android.content.BroadcastReceiver
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.google.android.gms.internal.i
import com.kotlineproject.adapter.ChatAdapter
import com.kotlineproject.model.MessageModel
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    val data = ArrayList<MessageModel>()
    lateinit var rcl_chat: RecyclerView
    lateinit var edt_message: EditText
    lateinit var adapter: ChatAdapter
    lateinit var img_attach: ImageView
    var seller_id: String? = ""
    var uid = ""
    // lateinit var seller_info: SellerModel
    // lateinit var user_info: UserModel // My Info
    var content = ""
    lateinit var receiver: BroadcastReceiver
    lateinit var selected_image: Bitmap

    var flagd: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Chat Example")


        rcl_chat = findViewById(R.id.recv_chat) as RecyclerView
        edt_message = findViewById(R.id.edt_message) as EditText
        img_attach = findViewById(R.id.img_attach) as ImageView

        adapter = ChatAdapter(this@ChatActivity, data, "")
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.reverseLayout = false
        rcl_chat.layoutManager = mLayoutManager
        rcl_chat.itemAnimator = DefaultItemAnimator()
        rcl_chat.adapter = adapter

        edt_message.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edt_message.right - edt_message.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    val df = SimpleDateFormat("M d hh:mm")
                    val dateobj = Date()

                    if (edt_message.text.toString() != "") {

                        content = edt_message.text.toString()

                        if (flagd == 0) {
                            flagd = 1;
                            val m = MessageModel("1", "" + content, "hardip", "jay", "0", "12-05-2018", "0", "")
                            data.add(m)
                        } else {
                            flagd = 0;
                            val m = MessageModel("1", "" + content, "hardip", "jay", "1", "12-05-2018", "0", "")
                            data.add(m)
                        }

                        adapter.notifyDataSetChanged()
                        rcl_chat.smoothScrollToPosition(View.FOCUS_UP)
                        edt_message.setText("")
                        content = ""

                        // GetChatRoom().execute("addMessage")
                        return@OnTouchListener true
                    }

                }
            }
            false
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        try {
            when (item!!.itemId) {
                android.R.id.home -> {
                    finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onOptionsItemSelected(item)
    }
}
