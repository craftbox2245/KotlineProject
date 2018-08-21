package com.kotlineproject.fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.kotlineproject.GlobalElements

import com.kotlineproject.R
import com.kotlineproject.adapter.CountryAdapter
import com.kotlineproject.adapter.ImageAdapter
import com.kotlineproject.custome.DividerItemDecoration
import com.kotlineproject.model.CountryModel
import com.kotlineproject.model.ImageModel
import com.kotlineproject.netUtils.RequestInterface
import com.kotlineproject.netUtils.RetrofitClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageLoaderFragment : Fragment() {

    lateinit var recycleView: RecyclerView
    lateinit var staticBtn: Button
    lateinit var liveBtn: Button
    lateinit var data: ArrayList<ImageModel>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_recycleview, container, false)
        data = ArrayList<ImageModel>()

        recycleView = rootView.findViewById(R.id.recycleView) as RecyclerView
        staticBtn = rootView.findViewById(R.id.static_btn) as Button
        liveBtn = rootView.findViewById(R.id.live_btn) as Button

        staticBtn.visibility=View.GONE;
        liveBtn.visibility=View.GONE;

        staticBtn.setOnClickListener {
            LoadStaticData()
        }

        LoadStaticData()

        return rootView
    }

    fun LoadStaticData() {
        try {
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

            da = ImageModel()
            da.id = "4"
            da.name = "https://i.amz.mshcdn.com/OX189-eu7aKokv4RPff1R8lFB78=/fit-in/1200x9600/https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com%2Fuploads%2Fcard%2Fimage%2F731883%2Fef36741e-2b02-44d4-8b1f-ca14565d1f0c.jpg"
            data.add(da);

            da = ImageModel()
            da.id = "5"
            da.name = "https://www.pxwall.com/wp-content/uploads/2018/06/Stock%20Images%20love%20image,%20heart,%20HD,%20island,%20ocean,%20Stock%20Images%206378311261.jpg"
            data.add(da);

            recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            val adapter = ImageAdapter(data, activity!!.applicationContext)
            val itemDecoration = DividerItemDecoration(activity!!.applicationContext)
            ///recycleView.addItemDecoration(itemDecoration)
            recycleView.adapter = adapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*fun getBranch() {

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
                            val d = customer_branch.getJSONObject(i)
                            var da = CountryModel()
                            da.id = "" + d.getString("id")
                            da.name = "" + d.getString("name")
                            data.add(da);
                        }

                        recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                        val adapter = CountryAdapter(data, activity!!.applicationContext)
                        val itemDecoration = DividerItemDecoration(activity!!.applicationContext)
                        recycleView.addItemDecoration(itemDecoration)
                        recycleView.adapter = adapter
                    } else {
                        Toast.makeText(activity, "" + json.getString("ack_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                pd.dismiss()
            }
        })
    }*/

}
