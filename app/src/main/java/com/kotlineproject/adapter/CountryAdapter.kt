package com.kotlineproject.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlineproject.R
import com.kotlineproject.model.CountryModel
import android.view.Gravity
import android.widget.LinearLayout
import android.os.Build
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.PopupWindow

/**
 * Created by CRAFT BOX on 10/17/2017.
 */
class CountryAdapter(val userList: ArrayList<CountryModel>, val context: Context, val fragment: Fragment) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    lateinit var mPopupWindow: PopupWindow;

    interface abc {
        fun b(position: Int)
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_country, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = userList.get(position).name

        holder.moreImg.setOnClickListener {
            try {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val customView = inflater.inflate(R.layout.layout_task_more, null)
                mPopupWindow = PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT, true
                )

                mPopupWindow.setOutsideTouchable(true)
                mPopupWindow.setFocusable(true)
                mPopupWindow.setTouchInterceptor(View.OnTouchListener { view, motionEvent ->
                    if (motionEvent.action == MotionEvent.ACTION_OUTSIDE) {
                        mPopupWindow.dismiss()
                        return@OnTouchListener true
                    }
                    false
                })
                // Call requires API level 21
                if (Build.VERSION.SDK_INT >= 21) {
                    //mPopupWindow.setElevation(5)
                    mPopupWindow.elevation = 5f
                }

                val change_layout = customView.findViewById(R.id.change_layout) as LinearLayout
                val view_layout = customView.findViewById(R.id.view_layout) as LinearLayout
                val editLayout = customView.findViewById(R.id.edit_layout) as LinearLayout
                val deleteLayout = customView.findViewById(R.id.delete_layout) as LinearLayout
                val hideLayout = customView.findViewById(R.id.hide_layout) as LinearLayout
                val hidetxt = customView.findViewById(R.id.hide_txt) as TextView
                val noteLayout = customView.findViewById(R.id.note_layout) as LinearLayout
                val attachmentLayout = customView.findViewById(R.id.attachment_layout) as LinearLayout

                change_layout.setOnClickListener { mPopupWindow.dismiss() }
                mPopupWindow.showAtLocation(holder.moreImg, Gravity.RIGHT or Gravity.RIGHT, 50, 30)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        holder.textViewName.setOnClickListener {
            try {
                ((fragment) as abc).b(position)
            } catch (e: Exception) {
                System.out.print("" + e.localizedMessage)
            }
        }
    }

    fun removeAt(position: Int) {
        userList.removeAt(position)
        notifyItemRemoved(position)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById(R.id.country_name) as TextView
        val moreImg = itemView.findViewById(R.id.more_img) as ImageView
    }
}