package com.kotlineproject

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SearchView
import com.kotlineproject.adapter.GeneralAdapter
import com.kotlineproject.model.GeneralModel
import fr.ganfra.materialspinner.MaterialSpinner

class SearchableSpinner : AppCompatActivity() {

    lateinit var spin_outlet: MaterialSpinner
    lateinit var adapter: GeneralAdapter
    lateinit var buildInfosDialog: AlertDialog
    lateinit var data: ArrayList<GeneralModel>

    var city_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable_spinner)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Searchable Spinner")

        spin_outlet = findViewById(R.id.searchable_spinner) as MaterialSpinner

        data = ArrayList<GeneralModel>()
        data.clear()

        var da = GeneralModel()
        da.id = "1"
        da.name = "India"
        data.add(da);

        da = GeneralModel()
        da.id = "2"
        da.name = "China"
        data.add(da);

        da = GeneralModel()
        da.id = "3"
        da.name = "America"
        data.add(da);

        da = GeneralModel()
        da.id = "4"
        da.name = "Japan"
        data.add(da);

        da = GeneralModel()
        da.id = "5"
        da.name = "Iran"
        data.add(da);

        da = GeneralModel()
        da.id = "6"
        da.name = "Caneda"
        data.add(da);
        da = GeneralModel()
        da.id = "7"
        da.name = "spain"
        data.add(da);
        da = GeneralModel()
        da.id = "8"
        da.name = "London"
        data.add(da);
        da = GeneralModel()
        da.id = "9"
        da.name = "Dubai"
        data.add(da);

        da = GeneralModel()
        da.id = "10"
        da.name = "Sri Lanka"
        data.add(da);

        //recycleView.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        adapter = GeneralAdapter(this@SearchableSpinner, data)
        spin_outlet.adapter = adapter
        adapter.notifyDataSetChanged()
        spin_outlet.hint = "Select Country"
        spin_outlet.isClickable = false

        spin_outlet.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    try {
                        val alertDialog2 = AlertDialog.Builder(this@SearchableSpinner)
                        alertDialog2.setTitle("Select City")
                        val inflater = this@SearchableSpinner.layoutInflater
                        val dialogView = inflater.inflate(R.layout.searchable_list_dialog, null)
                        alertDialog2.setView(dialogView)
                        val lst = dialogView.findViewById(R.id.listItems) as ListView
                        val searchView = dialogView.findViewById(R.id.search) as SearchView
                        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
                        searchView.setSearchableInfo(searchManager!!.getSearchableInfo(componentName))
                        searchView.setIconifiedByDefault(false)
                        searchView.clearFocus()
                        val mgr = this@SearchableSpinner.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        mgr!!.hideSoftInputFromWindow(searchView.windowToken, 0)
                        adapter = GeneralAdapter(this@SearchableSpinner, data)
                        lst.adapter = adapter
                        adapter.notifyDataSetChanged()

                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String): Boolean {
                                return false
                            }

                            override fun onQueryTextChange(newText: String): Boolean {
                                adapter.filter(newText)
                                return false
                            }
                        })

                        alertDialog2.setView(dialogView)

                        lst.onItemClickListener = object : AdapterView.OnItemClickListener {
                            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                try {
                                    for (k in data.indices) {
                                        if (data.get(k).name.toString().equals("" + data.get(position).name.toString())) {

                                            try {
                                                city_id = data.get(k).id
                                                val cname = "" + data.get(k).id
                                                adapter.filter("")
                                                for (h in data.indices) {
                                                    if (data.get(h).id.toString().equals("" + cname)) {
                                                        spin_outlet.setSelection(h + 1)
                                                        break
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                            break
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                buildInfosDialog.dismiss()
                            }
                        }
                        alertDialog2.setPositiveButton("Close"
                        ) { dialog, which ->
                            // Write your code here to execute after dialog
                            adapter.filter("")
                        }
                        buildInfosDialog = alertDialog2.create()
                        buildInfosDialog.show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
