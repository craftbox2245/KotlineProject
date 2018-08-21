package com.kotlineproject.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import com.kotlineproject.R
import com.kotlineproject.model.ImageModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

import java.util.ArrayList

class CustomPagerAdapter(private val mContext: Context, list: ArrayList<ImageModel>) : PagerAdapter() {
    internal var list = ArrayList<ImageModel>()
    private var layoutInflater: LayoutInflater? = null
    private var options: DisplayImageOptions? = null
    protected var imageLoader = ImageLoader.getInstance()

    init {
        this.list = list
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.list_image, container, false)

        try {
            val img: ImageView
            img = view.findViewById(R.id.image_name) as ImageView

            if (!list.get(position).name.equals("")) {
                imageLoader.init(ImageLoaderConfiguration.createDefault(mContext))
                options = DisplayImageOptions.Builder()
                        .showImageForEmptyUri(R.drawable.logo)
                        .showImageOnFail(R.drawable.logo)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build()
                imageLoader.displayImage(list.get(position).name, img, options)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(ssContainer: ViewGroup, ssPosition: Int,
                             ssObject: Any) {
        (ssContainer as ViewPager).removeView(ssObject as LinearLayout)
    }

    override fun isViewFromObject(ssView: View, ssObject: Any): Boolean {
        return ssView === ssObject as LinearLayout
    }

    override fun getCount(): Int {
        return list.size
    }

}