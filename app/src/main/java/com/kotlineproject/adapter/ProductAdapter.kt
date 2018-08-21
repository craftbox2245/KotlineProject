package com.kotlineproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlineproject.R
import com.kotlineproject.model.CountryModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration


/**
 * Created by CRAFT BOX on 10/17/2017.
 */
class ProductAdapter(val userList: ArrayList<CountryModel>, val context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var options: DisplayImageOptions? = null
    protected var imageLoader = ImageLoader.getInstance()

    interface abc {
       fun b(position: Int)
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_product_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            if (!userList.get(position).name.equals("")) {
                imageLoader.init(ImageLoaderConfiguration.createDefault(context))
                options = DisplayImageOptions.Builder()
                        .showImageForEmptyUri(R.drawable.logo)
                        .showImageOnFail(R.drawable.logo)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build()
                imageLoader.displayImage(userList.get(position).name, holder.imageName, options)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageName = itemView.findViewById(R.id.list_home_img) as ImageView
    }
}