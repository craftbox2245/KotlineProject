package com.kotlineproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import butterknife.ButterKnife
import com.google.android.gms.internal.s
import com.kotlineproject.adapter.MyListAdapter
import com.kotlineproject.custome.NonScrollExpandableListView
import com.kotlineproject.model.SubCategoryModel
import com.kotlineproject.model.SubSubCategoryModel
import java.util.ArrayList

class ExpandabelListActivity : AppCompatActivity() {

    private var myList: NonScrollExpandableListView? = null
    private val data = ArrayList<SubCategoryModel>()
    private var listAdapter: MyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandabel_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Expandable Listview")

        myList = findViewById(R.id.expandableList) as NonScrollExpandableListView

        try {
            var d = SubCategoryModel()
            d.id = 1;
            d.name = "first category"

            var sub = ArrayList<SubSubCategoryModel>()
            var da = SubSubCategoryModel()
            da.id = 1
            da.name = "Child 1"
            da.sid = 1
            sub.add(da)
            da = SubSubCategoryModel()
            da.id = 2
            da.name = "Child 2"
            da.sid = 1
            sub.add(da)

            d.subsubcategory = sub
            data.add(d)

            d = SubCategoryModel()
            d.id = 2;
            d.name = "Second category"

            sub = ArrayList<SubSubCategoryModel>()
            da = SubSubCategoryModel()
            da.id = 1
            da.name = "Child 3"
            da.sid = 2
            sub.add(da)
            da = SubSubCategoryModel()
            da.id = 2
            da.name = "Child 4"
            da.sid = 2
            sub.add(da)

            d.subsubcategory = sub
            data.add(d)

            d = SubCategoryModel()
            d.id = 3;
            d.name = "Third category"

            sub = ArrayList<SubSubCategoryModel>()
            da = SubSubCategoryModel()
            da.id = 1
            da.name = "Child 3"
            da.sid = 3
            sub.add(da)
            da = SubSubCategoryModel()
            da.id = 2
            da.name = "Child 4"
            da.sid = 3
            sub.add(da)

            d.subsubcategory = sub
            data.add(d)

            listAdapter = MyListAdapter(this@ExpandabelListActivity, data)
            myList!!.setAdapter(listAdapter)
            listAdapter!!.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
