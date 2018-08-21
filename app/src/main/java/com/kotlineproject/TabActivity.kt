package com.kotlineproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.kotlineproject.model.CountryModel
import android.R.attr.data
import com.kotlineproject.model.GeneralModel
import com.kotlineproject.pageAdapter.TabPagerAdapter


class TabActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var data: ArrayList<GeneralModel>
    lateinit var pagerAdapter: TabPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        data = ArrayList<GeneralModel>()

        tabLayout = findViewById(R.id.tab_layout) as TabLayout
        viewPager = findViewById(R.id.view_pager) as ViewPager

        var da = GeneralModel()
        da.id = "1"
        da.name = "RecycleView"
        data.add(da)

        da = GeneralModel()
        da.id = "2"
        da.name = "Product Grid"
        data.add(da)

        pagerAdapter = TabPagerAdapter(this.supportFragmentManager, data)
        viewPager.adapter = pagerAdapter
        pagerAdapter.notifyDataSetChanged()
        tabLayout.setupWithViewPager(viewPager)

    }
}
