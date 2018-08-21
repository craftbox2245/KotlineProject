package com.kotlineproject.pageAdapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


import com.kotlineproject.fragment.ProductFragment
import com.kotlineproject.fragment.RecycleViewFragment
import com.kotlineproject.model.GeneralModel

import java.util.ArrayList

/**
 * Created by CRAFT BOX on 12/23/2016.
 */

class TabPagerAdapter(fm: FragmentManager, topCategoryModels: ArrayList<GeneralModel>) : FragmentPagerAdapter(fm) {
    lateinit var f: Fragment
    internal var mFragmentList: MutableList<Fragment> = ArrayList()
    internal var categoryModels = ArrayList<GeneralModel>()

    init {
        this.categoryModels = topCategoryModels
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categoryModels[position].name
    }

    override fun getCount(): Int {
        return categoryModels.size
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            f = RecycleViewFragment()
            mFragmentList.add(f)
            return f
        } else {
            f = ProductFragment()
            mFragmentList.add(f)
            return f
        }
    }
}
