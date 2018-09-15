package com.kotlineproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.kotlineproject.R
import com.kotlineproject.model.GeneralModel

import java.util.ArrayList
import java.util.Locale

/**
 * Created by CRAFT BOX on 10/25/2016.
 */

class GeneralAdapter(private val activity: Context, da: ArrayList<GeneralModel>) : BaseAdapter() {
    private var data: ArrayList<GeneralModel>? = null
    internal var arraylist = ArrayList<GeneralModel>()
    private var inflater1: LayoutInflater? = null
    internal var string: String? = null

    init {
        data = da
        arraylist.addAll(da)
    }

    override fun getCount(): Int {
        return data!!.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var vi = convertView
        if (convertView == null) {
            inflater1 = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vi = inflater1!!.inflate(R.layout.list_general, null)
        }
        val name: TextView
        name = vi!!.findViewById<View>(R.id.general_name) as TextView
        name.text = data!![position].name
        return vi
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        data!!.clear()
        if (charText.length == 0) {
            data!!.addAll(arraylist)
        } else {
            for (wp in arraylist) {
                if (wp.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    data!!.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}
