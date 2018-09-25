package com.kotlineproject.adapter

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.kotlineproject.R
import com.kotlineproject.custome.ScaleImageView
import com.kotlineproject.model.MessageModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

import java.io.File
import java.util.ArrayList

/**
 * Created by Suleiman on 26-07-2015.
 */
class ChatAdapter(internal var context: Context, da: ArrayList<MessageModel>, internal var uid: String) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    internal var data = ArrayList<MessageModel>()
    private var options: DisplayImageOptions? = null
    protected var imageLoader = ImageLoader.getInstance()
    internal var img_name: Long = 0

    init {
        this.data = da
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.single_chat_item, parent, false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (data[position].flag == "0") {

            if (data[position].message_type == "1") {
                val params = LinearLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.RIGHT
                params.setMargins(5, 5, 5, 5)
                holder.cc_message.layoutParams = params

                holder.img_message.visibility = View.VISIBLE
                holder.message.visibility = View.GONE

                imageLoader.init(ImageLoaderConfiguration.createDefault(context))
                options = DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.logo)
                        .showImageForEmptyUri(R.drawable.logo)
                        .showImageOnFail(R.drawable.logo)
                        .build()
                imageLoader.displayImage(data[position].attachmenr_url, holder.img_message, options)

                //   holder.img_message.setImageBitmap(data.get(position).getImage());
                holder.message.text = "" + data[position].message
                holder.time.text = "" + data[position].time
            } else {
                val params = LinearLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.RIGHT
                params.setMargins(5, 5, 5, 5)
                holder.cc_message.layoutParams = params

                holder.message.visibility = View.VISIBLE
                holder.img_message.visibility = View.GONE
                holder.message.text = "" + data[position].message
                holder.time.text = "" + data[position].time
            }
        } else if (data[position].flag == "1") {
            if (data[position].message_type == "1") {
                val params = LinearLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.LEFT
                params.setMargins(5, 5, 5, 5)
                holder.cc_message.layoutParams = params
                holder.img_message.visibility = View.VISIBLE
                holder.message.visibility = View.GONE

                imageLoader.init(ImageLoaderConfiguration.createDefault(context))
                options = DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.logo)
                        .showImageForEmptyUri(R.drawable.logo)
                        .showImageOnFail(R.drawable.logo)
                        .build()
                imageLoader.displayImage(data[position].attachmenr_url, holder.img_message, options)

                // holder.img_message.setImageBitmap(data[position].image)
                holder.message.text = "" + data[position].message
                holder.time.text = "" + data[position].time
            } else {
                val params = LinearLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.LEFT
                params.setMargins(5, 5, 5, 5)
                holder.cc_message.layoutParams = params

                holder.message.visibility = View.VISIBLE
                holder.img_message.visibility = View.GONE
                holder.message.text = "" + data[position].message
                holder.time.text = "" + data[position].time
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message: TextView
        var time: TextView
        var img_message: ScaleImageView
        var cc_message: CardView

        init {
            message = itemView.findViewById<View>(R.id.tv_message) as TextView
            time = itemView.findViewById<View>(R.id.tv_time) as TextView
            img_message = itemView.findViewById<View>(R.id.img_message) as ScaleImageView
            cc_message = itemView.findViewById<View>(R.id.cc_message) as CardView
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Log.d("MyAdapter", "onActivityResult")
        try {
            val root = Environment.getExternalStorageDirectory()
            val cachePath = File(root.absolutePath + "/DCIM/Camera/'" + img_name + "'.jpg")
            val deleted = cachePath.delete()
            img_name = System.currentTimeMillis()
            Log.d("MyAdapter", "onActivityResult")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
