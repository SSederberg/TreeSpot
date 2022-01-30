package net.n4dev.treespot.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView
import net.n4dev.treespot.R
import net.n4dev.treespot.databinding.ActivityMainBinding
import net.n4dev.treespot.ui.fragments.friends.MyFriendsFragment
import net.n4dev.treespot.ui.fragments.spots.MySpotsFragment

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

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(activeMenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.bottom_nav_friends -> {
                fragmentManager.commit {
                    replace<MyFriendsFragment>(R.id.main_fragment_container)
                }
                activeMenu = R.menu.menu_main_friends
                invalidateOptionsMenu()

                return true
            }

            R.id.bottom_nav_spots -> {
            fragmentManager.commit {
                    replace<MySpotsFragment>(R.id.main_fragment_container)
                }

                activeMenu = R.menu.menu_main_spots
                invalidateOptionsMenu()
                return true
            }

            R.id.bottom_nav_take_spot -> {
                fragmentManager.commit {
                    replace<MyFriendsFragment>(R.id.main_fragment_container)
                }

                activeMenu = R.menu.menu_main_capture_spot
                invalidateOptionsMenu()
                return true
            }
        }

        return false
    }


}