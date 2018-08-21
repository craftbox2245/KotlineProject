package com.kotlineproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.kotlineproject.adapter.CustomPagerAdapter
import com.kotlineproject.model.ImageModel

class PagerActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var data: ArrayList<ImageModel>
    lateinit var adapter: CustomPagerAdapter
    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        data = ArrayList<ImageModel>()
        viewPager = findViewById(R.id.viewpager) as ViewPager

        try {
            type = intent.getStringExtra("type")
            if (type.equals("1")) {
                data = intent.extras.get("data") as ArrayList<ImageModel>
            }
        } catch (e: Exception) {
            e.printStackTrace()
            type = "0";
        }

        try {
            if (type.equals("0")) {
                data = ArrayList<ImageModel>()
                data.clear()
                var da = ImageModel()
                da.id = "1"
                da.name = "https://www.gettyimages.com.au/gi-resources/images/Homepage/Hero/US/Oct2016/VQ_The%20Live-Wire.jpg"
                data.add(da);

                da = ImageModel()
                da.id = "2"
                da.name = "https://wallpaper-house.com/data/out/10/wallpaper2you_394463.jpg"
                data.add(da);

                da = ImageModel()
                da.id = "3"
                da.name = "https://www.diymauritius.com/wp-content/uploads/2018/04/spyro-trilogys-remastered-graphics-showcased-in-comparison-images.png"
                data.add(da);
            }
            adapter = CustomPagerAdapter(this@PagerActivity, data)
            viewPager.setAdapter(adapter)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
