package net.n4dev.treespot.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView
import net.n4dev.treespot.R
import net.n4dev.treespot.databinding.ActivityMainBinding
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.main.fragments.capture.CaptureSpotFragment
import net.n4dev.treespot.ui.settings.SettingsActivity
import net.n4dev.treespot.util.ActivityUtil

class MainActivity : TreeSpotActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private var activeMenu = R.menu.menu_main_friends

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        binding.mainBottomNavigation.setOnItemSelectedListener(this)

        fragmentManager.commit {
            replace<CaptureSpotFragment>(R.id.main_fragment_container)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(activeMenu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(activeMenu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.bottom_nav_friends -> {
                ActivityUtil.toast(this,  "nav_friends", false)

                activeMenu = R.menu.menu_main_friends
                invalidateOptionsMenu()

                return true
            }

            R.id.bottom_nav_my_spots -> {
                ActivityUtil.toast(this,  "nav_my_spots", false)


                activeMenu = R.menu.menu_main_spots
                invalidateOptionsMenu()
                return true
            }

            R.id.bottom_nav_take_spot -> {
                ActivityUtil.toast(this,  "nav_take_spot", false)

                activeMenu = R.menu.menu_main_capture_spot
                invalidateOptionsMenu()
                return true
            }
        }

        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemID = item.itemId;

        if(itemID == R.id.menu_main_capture_settings) {
            ActivityUtil.startActivity(SettingsActivity::class.java, this)
            return true
        }
        return true
    }
}