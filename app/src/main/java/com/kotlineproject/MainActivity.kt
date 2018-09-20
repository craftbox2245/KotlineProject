package com.kotlineproject

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kotlineproject.adapter.CountryAdapter
import com.kotlineproject.dialog.AddtocartDialog
import com.kotlineproject.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        try {
            val firstFragment = firstFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.container, firstFragment)
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 23) {
                val fragments = supportFragmentManager.fragments
                if (fragments != null) {
                    for (fragment in fragments) {
                        if (fragment is GalleryPickFragment) {
                            fragment.onActivityResult(requestCode, resultCode, data)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_first -> {
                // Handle the camera action
                try {
                    val firstFragment = firstFragment()
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, firstFragment)
                    transaction.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.nav_second -> {
                try {
                    val firstFragment = SecondFragment()
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, firstFragment)
                    transaction.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.nav_recycleview -> {
                try {
                    val firstFragment = RecycleViewFragment()
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, firstFragment)
                    transaction.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.nav_recycleview_grid -> {
                try {
                    val firstFragment = ProductFragment()
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, firstFragment)
                    transaction.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.nav_gallery -> {
                try {
                    val firstFragment = GalleryPickFragment()
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, firstFragment)
                    transaction.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.nav_image_loder -> {
                try {
                    val firstFragment = ImageLoaderFragment()
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, firstFragment)
                    transaction.commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.nav_dialog -> {
                val fm = (this@MainActivity as FragmentActivity).supportFragmentManager
                val rd = AddtocartDialog.newInstance()
                rd.show(fm, "")
            }
            R.id.nav_pag -> {
                val intent = Intent(this@MainActivity, PagerActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_tab -> {
                val intent = Intent(this@MainActivity, TabActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_broadcast -> {
                val intent = Intent(this@MainActivity, OtpVerificationActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_map -> {
                val intent = Intent(this@MainActivity, LocationActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_spinner -> {
                val intent = Intent(this@MainActivity, SearchableSpinner::class.java)
                startActivity(intent)
            }
            R.id.nav_expandable ->{
                val intent = Intent(this@MainActivity, ExpandabelListActivity::class.java)
                startActivity(intent)
            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
