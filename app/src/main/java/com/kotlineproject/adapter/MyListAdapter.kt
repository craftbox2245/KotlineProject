package com.kotlineproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


import com.kotlineproject.GlobalElements
import com.kotlineproject.R
import com.kotlineproject.model.SubCategoryModel
import com.kotlineproject.model.SubSubCategoryModel

import java.util.ArrayList

/**
 * Created by CRAFT BOX on 11/23/2016.
 */

class MyListAdapter(private val context: Context, continentList: ArrayList<SubCategoryModel>) : BaseExpandableListAdapter() {
    private val continentList: ArrayList<SubCategoryModel>
    private val originalList: ArrayList<SubCategoryModel>

    init {
        this.continentList = ArrayList()
        this.continentList.addAll(continentList)
        this.originalList = ArrayList()
        this.originalList.addAll(continentList)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val countryList = continentList[groupPosition].subsubcategory
        return countryList[childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean,
                              view: View?, parent: ViewGroup): View {
        var view = view

        val subsub = getChild(groupPosition, childPosition) as SubSubCategoryModel
        if (view == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.child_row, null)
        }
        val layout = view!!.findViewById<View>(R.id.child_row_linear) as LinearLayout

        val name = view.findViewById<View>(R.id.name) as TextView
        name.text = subsub.name

        name.setOnClickListener {
            /*Intent i=new Intent(context, ProductActivity.class);
                i.putExtra("ssid",""+subsub.getId());
                i.putExtra("sid",""+subsub.getSid());
                i.putExtra("title",""+subsub.getName());
                i.putExtra("type","category");
                context.startActivity(i);*/
        }

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val countryList = continentList[groupPosition].subsubcategory
        return countryList.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return continentList[groupPosition]
    }

    override fun getGroupCount(): Int {
        return continentList.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, view: View?,
                              parent: ViewGroup): View {
        var view = view
        val data = getGroup(groupPosition) as SubCategoryModel
        if (view == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.group_row, null)
        }
        val img = view!!.findViewById<View>(R.id.group_row_indictor) as ImageView
        if (isExpanded) {
            img.setImageResource(R.drawable.ic_down_arrow_24dp)
        } else {
            img.setImageResource(R.drawable.ic_right_arrow_24dp)
        }
        val layout = view.findViewById<View>(R.id.group_row_linear) as LinearLayout
        //GlobalElements.overrideFonts_P_Nova_Rg_Regular(context,layout);
        val heading = view.findViewById<View>(R.id.heading) as TextView

        heading.text = data.name

        return view
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    /*public void filterData(String query){

        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        continentList.clear();

        if(query.isEmpty()){
            continentList.addAll(originalList);
        }
        else {

            for(Continent continent: originalList){

                ArrayList<Country> countryList = continent.getCountryList();
                ArrayList<Country> newList = new ArrayList<Country>();
                for(Country country: countryList){
                    if(country.getCode().toLowerCase().contains(query) ||
                            country.getName().toLowerCase().contains(query)){
                        newList.add(country);
                    }
                }
                if(newList.size() > 0){
                    Continent nContinent = new Continent(continent.getName(),newList);
                    continentList.add(nContinent);
                }
            }
        }
        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        notifyDataSetChanged();
    }*/
}
