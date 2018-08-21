package com.kotlineproject.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlineproject.MainActivity
import com.kotlineproject.PagerActivity
import com.kotlineproject.R
import com.kotlineproject.model.CountryModel
import com.kotlineproject.model.ImageModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import java.io.Serializable


/**
 * Created by CRAFT BOX on 10/17/2017.
 */
class ImageAdapter(val data: ArrayList<ImageModel>, val context: Context) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var options: DisplayImageOptions? = null
    protected var imageLoader = ImageLoader.getInstance()

    interface abc {
        fun b(position: Int)
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_image, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            if (!data.get(position).name.equals("")) {
                imageLoader.init(ImageLoaderConfiguration.createDefault(context))
                options = DisplayImageOptions.Builder()
                        .showImageForEmptyUri(R.drawable.logo)
                        .showImageOnFail(R.drawable.logo)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build()
                imageLoader.displayImage(data.get(position).name, holder.imageName, options)
            }

            holder.imageName.setOnClickListener {
                try {
                    val int = Intent(context, PagerActivity::class.java);
                    int.putExtra("type", "1")
                    int.putExtra("data", data)
                    context.startActivity(int)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return data.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageName = itemView.findViewById(R.id.image_name) as ImageView
    }
}