package com.kotlineproject.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SimpleAdapter
import android.widget.Toast
import com.kotlineproject.GlobalElements

import com.kotlineproject.R
import com.kotlineproject.adapter.CountryAdapter
import com.kotlineproject.custome.*
import com.kotlineproject.model.CountryModel
import com.kotlineproject.netUtils.DBHelper
import com.kotlineproject.netUtils.RequestInterface
import com.kotlineproject.netUtils.RetrofitClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecycleViewFragment : Fragment(), CountryAdapter.abc {

    override fun b(position: Int) {
        System.out.print("" + position)
    }

    lateinit var recycleView: RecyclerView
    lateinit var staticBtn: Button
    lateinit var liveBtn: Button
    lateinit var data: ArrayList<CountryModel>
    lateinit var db: DBHelper
    lateinit var adapter: CountryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_recycleview, container, false)
        data = ArrayList<CountryModel>()
        db = DBHelper(activity!!.applicationContext)

        recycleView = rootView.findViewById(R.id.recycleView) as RecyclerView
        staticBtn = rootView.findViewById(R.id.static_btn) as Button
        liveBtn = rootView.findViewById(R.id.live_btn) as Button

        staticBtn.setOnClickListener {
            LoadStaticData()
        }

        liveBtn.setOnClickListener {
            try {
                if (GlobalElements.isConnectingToInternet(activity!!.applicationContext)) {
                    getBranch()
                } else {
                    GlobalElements.showDialog(activity!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val swipeHandler = object : SwipeToDeleteCallback(activity!!.applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recycleView.adapter as CountryAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycleView)

        return rootView
    }

    fun LoadStaticData() {
        try {
            data = ArrayList<CountryModel>()
            data.clear()

            var da = CountryModel()
            da.id = "1"
            da.name = "India"
            data.add(da)

            da = CountryModel()
            da.id = "2"
            da.name = "Pakistan"
            data.add(da)

            da = CountryModel()
            da.id = "3"
            da.name = "America"
            data.add(da)

            da = CountryModel()
            da.id = "4"
            da.name = "China"
            data.add(da)

            da = CountryModel()
            da.id = "5"
            da.name = "Japan"
            data.add(da)


            val c = db.getData("select * from " + DBHelper.COUNTRY + " ")
            if (c!!.count > 0) {
                data = ArrayList<CountryModel>()
                data.clear()
                while (c.moveToNext()) {
                    val da = CountryModel()
                    da.id = c.getString(c.getColumnIndex("id"))
                    da.name = c.getString(c.getColumnIndex("name"))
                    data.add(da)
                }
            } else {
                System.out.print("")
            }

            recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            adapter = CountryAdapter(data, activity!!.applicationContext, this)
            val itemDecoration = DividerItemDecoration(activity!!.applicationContext)
            recycleView.addItemDecoration(itemDecoration)
            recycleView.adapter = adapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getBranch() {

        val pd = ProgressDialog(activity)
        pd.setTitle("Please Wait")
        pd.setMessage("Loading")
        pd.setCancelable(true)
        pd.show()
        val req = RetrofitClient.getClient().create(RequestInterface::class.java)
        val call = req.getcustomerDetail()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                pd.dismiss()
                try {
                    val jsonst = response.body()!!.string()
                    val json = JSONObject(jsonst)
                    data = ArrayList<CountryModel>()
                    data.clear()

                    if (json.getInt("ack") == 1) {
                        val customer_branch = json.getJSONArray("result")
                        for (i in 0..customer_branch.length() - 1) {
                            var d = customer_branch.getJSONObject(i)
                            var da = CountryModel()
                            da.id = "" + d.getString("id")
                            da.name = "" + d.getString("name")
                            data.add(da)
                        }

                        recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                        adapter = CountryAdapter(data, activity!!.applicationContext, this@RecycleViewFragment)
                        val itemDecoration = DividerItemDecoration(activity!!.applicationContext)
                        recycleView.addItemDecoration(itemDecoration)
                        recycleView.adapter = adapter
                    } else {
                        Toast.makeText(activity, "" + json.getString("ack_msg"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pd.dismiss()
            }
        })
    }

}
